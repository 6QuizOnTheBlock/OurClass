package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.CommentChildrenDTO;
import com.quiz.ourclass.domain.board.dto.CommentDTO;
import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Image;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.board.mapper.CommentMapper;
import com.quiz.ourclass.domain.board.mapper.PostMapper;
import com.quiz.ourclass.domain.board.repository.CommentRepository;
import com.quiz.ourclass.domain.board.repository.ImageRepository;
import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final AwsS3ObjectStorage awsS3ObjectStorage;
    private final UserAccessUtil userAccessUtil;
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;


    @Transactional
    @Override
    public Long write(Long classId, MultipartFile file, PostRequest request)
        throws IOException {
        //TODO : Mapper 사용해서 로직 구성해야함 (추후 리팩토링 필요)
        //멤버가 존재하는지 확인
        Member member = userAccessUtil.getMember();

        //멤버가 쿼리 파라미터로 들어온 단체에 속해있는지 확인(classId)
        MemberOrganization memberOrganization =
            userAccessUtil.isMemberOfOrganization(member, classId);

        //S3 이미지 파일 업로드
        Image image = null;
        if (file != null && !file.isEmpty()) {
            String url = awsS3ObjectStorage.uploadFile(file);
            if (url.isEmpty()) {
                throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
            }
            image = imageRepository.save(
                new Image(file.getOriginalFilename(), url, LocalDateTime.now()));
        }
        //게시글 저장하기
        Post post = new Post(member, memberOrganization.getOrganization(), image, request);
        return postRepository.save(post).getId();
    }

    @Transactional
    @Override
    public Long modify(Long id, MultipartFile file, PostRequest request)
        throws IOException {
        //TODO : Mapper 사용해서 로직 구성해야함 (추후 리팩토링 필요)
        //게시글을 수정할 수 있는 멤버인지 검증
        Member member = userAccessUtil.getMember();

        //수정 할 게시글 조회
        Post post = postRepository.findByIdAndAuthor(id, member)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_EDIT_PERMISSION_DENIED));

        //이미지 관련 처리 부분
        Image image = post.getImage();
        if (request.getImageDelete() && image != null) { //이미지를 삭제하는 경우
            awsS3ObjectStorage.deleteFile(image.getPath());
            imageRepository.delete(image);
            post.setImage(null);
        } else if (file != null && !file.isEmpty()) {  //이미지를 수정 할 경우 (Multipart 입력으로 들어온 경우)
            //기존 이미지가 있으면 S3 삭제
            if (image != null) {
                awsS3ObjectStorage.deleteFile(image.getPath());
            }
            //새 이미지 업로드
            String url = awsS3ObjectStorage.uploadFile(file);
            if (url.isEmpty()) {
                throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
            }
            if (image == null) { //이미지가 없었다면 새로 이미지 저장
                image = new Image(file.getOriginalFilename(), url, LocalDateTime.now());
            } else { //이미지 정보 수정
                image.setOriginalName(file.getOriginalFilename());
                image.setPath(url);
                image.setCreateTime(LocalDateTime.now());
            }
            image = imageRepository.save(image);
            post.setImage(image);
        }
        // 게시글 정보 변경
        post.setPostCategory(request.getType());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setSecretStatus(request.getAnonymous());

        return postRepository.save(post).getId();
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        //학생은 학생이 작성한 게시글만 삭제 가능
        //교사는 모든 게시글 삭제 가능
        Member member = userAccessUtil.getMember();
        Post post = null;
        if (member.getRole() == Role.STUDENT) {
            post = postRepository.findByIdAndAuthor(id, member)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_EDIT_PERMISSION_DENIED));
        } else if (member.getRole() == Role.TEACHER || member.getRole() == Role.ADMIN) {
            post = postRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));
        }
        if (post == null) {
            throw new GlobalException(ErrorCode.POST_NOT_FOUND);
        }
        postRepository.delete(post);
        return true;
    }

    @Override
    public PostDetailResponse detailView(Long id) {
        //게시글 조회
        Post post = postRepository.fetchPostWithDetails(id);
        if (post == null) { //게시글 없으면 예외처리
            throw new GlobalException(ErrorCode.POST_NOT_FOUND);
        }
        //게시글에 해당하는 댓글 List 조회
        List<Comment> comments = commentRepository.findAllByPostId(id);
        //댓글, 대댓글 필터 처리
        List<CommentDTO> parentComments = buildParentComments(comments);
        //게시글 mapping 작업
        return postMapper.postToPostDetailResponse(post, parentComments);
    }

    private List<CommentDTO> buildParentComments(List<Comment> comments) {
        return comments.stream()
            .filter(c -> c.getParentId() == 0L)
            .map(parentComment -> {
                List<CommentChildrenDTO> children = comments.stream()
                    .filter(c -> c.getParentId() != 0L && c.getParentId()
                        .equals(parentComment.getId()))
                    .map(commentMapper::commentToCommentChildrenDTO)
                    .toList();

                return commentMapper.commentToCommentDTOWithChildren(parentComment, children);
            })
            .toList();
    }
}
