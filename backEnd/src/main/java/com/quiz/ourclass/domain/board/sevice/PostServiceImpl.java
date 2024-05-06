package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.CommentChildrenDTO;
import com.quiz.ourclass.domain.board.dto.CommentDTO;
import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.request.PostSliceRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdatePostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.dto.response.PostListResponse;
import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Image;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.board.entity.PostCategory;
import com.quiz.ourclass.domain.board.mapper.CommentMapper;
import com.quiz.ourclass.domain.board.mapper.ImageMapper;
import com.quiz.ourclass.domain.board.mapper.PostMapper;
import com.quiz.ourclass.domain.board.repository.CommentRepository;
import com.quiz.ourclass.domain.board.repository.ImageRepository;
import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.notice.entity.Notice;
import com.quiz.ourclass.domain.notice.entity.NoticeType;
import com.quiz.ourclass.domain.notice.repository.NoticeRepository;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.global.dto.FcmDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import com.quiz.ourclass.global.util.FcmType;
import com.quiz.ourclass.global.util.FcmUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
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
    private final NoticeRepository noticeRepository;
    private final AwsS3ObjectStorage awsS3ObjectStorage;
    private final UserAccessUtil userAccessUtil;
    private final FcmUtil fcmUtil;
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final ImageMapper imageMapper;

    @Transactional
    @Override
    public Long write(Long organizationId, MultipartFile file, PostRequest request) {
        LocalDateTime now = LocalDateTime.now();
        //멤버가 존재하는지 확인
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        if (request.postCategory() == PostCategory.NOTICE && member.getRole() == Role.STUDENT) {
            throw new GlobalException(ErrorCode.POST_WRITE_PERMISSION_DENIED);
        }

        //멤버가 쿼리 파라미터로 들어온 단체에 속해있는지 확인
        MemberOrganization memberOrganization =
            userAccessUtil.isMemberOfOrganization(member, organizationId)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));

        //S3 이미지 파일 업로드
        Image image = null;
        if (file != null && !file.isEmpty()) {
            String url = awsS3ObjectStorage.uploadFile(file);
            if (url.isEmpty()) {
                throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
            }
            image = Image.builder()
                .originalName(file.getOriginalFilename())
                .path(url)
                .createTime(now)
                .build();
            imageRepository.save(image);
        }
        //게시글 저장하기
        Post post = postMapper.postRequestToPost(
            request, member, memberOrganization.getOrganization(), now, image
        );
        return postRepository.save(post).getId();
    }

    /*
     * boolean 값 여부로 확인해서 이미지 삭제
     * multipart 가 새로 들어오면 이미지 수정
     * multipart 가 아무것도 입력으로 오지 않으면 글만 수정 (이미지 수정 X)
     * */
    @Transactional
    @Override
    public Long modify(Long postId, MultipartFile file, UpdatePostRequest request) {
        LocalDateTime now = LocalDateTime.now();
        //게시글을 수정할 수 있는 멤버인지 검증
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        //수정 할 게시글 조회
        Post post = postRepository.findByIdAndAuthor(postId, member)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_EDIT_PERMISSION_DENIED));

        //이미지 관련 처리 부분
        Image image = post.getImage();
        if (request.imageDelete() && image != null) { //이미지를 삭제하는 경우
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
                image = Image.builder()
                    .originalName(file.getOriginalFilename())
                    .path(url)
                    .createTime(now)
                    .build();
            } else { //이미지 정보 수정
                imageMapper.updateImage(file.getOriginalFilename(), url, now, image);
            }
            image = imageRepository.save(image);
            post.setImage(image);
        }
        // 게시글 정보 변경
        postMapper.updatePostFromRequest(now, request, post);

        return postRepository.save(post).getId();
    }

    @Transactional
    @Override
    public Boolean delete(Long postId) {
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        Role requesterRole = member.getRole();
        if (requesterRole == Role.STUDENT) {
            if (post.getAuthor().getId() != member.getId()) {
                throw new GlobalException(ErrorCode.POST_DELETE_STUDENT_PERMISSION_DENIED);
            }
        } else if (requesterRole == Role.TEACHER) {
            Long orgId = post.getOrganization().getId();
            userAccessUtil.isMemberOfOrganization(member, orgId)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));
        }
        postRepository.delete(post);
        return true;
    }

    @Override
    public PostDetailResponse detailView(Long postId) {
        //게시글 조회
        Post post = postRepository.fetchPostWithDetails(postId);
        if (post == null) { //게시글 없으면 예외처리
            throw new GlobalException(ErrorCode.POST_NOT_FOUND);
        }
        //게시글에 해당하는 댓글 List 조회
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        //댓글, 대댓글 필터 처리
        List<CommentDTO> parentComments = buildParentComments(comments);
        //게시글 mapping 작업
        return postMapper.postToPostDetailResponse(post, parentComments);
    }

    @Transactional
    @Override
    public Boolean report(Long postId) {
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        userAccessUtil.isMemberOfOrganization(member, post.getOrganization().getId())
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));

        String reportMember = member.getName();
        String authorMember = post.getAuthor().getName();
        String title = fcmUtil.makeReportTitle(
            post.getOrganization().getName(), FcmType.POST.getType()
        );
        String body = fcmUtil.makeReportBody(
            authorMember, reportMember, FcmType.POST.getType()
        );

        FcmDTO fcmDTO = fcmUtil.makeFcmDTO(title, body);

        // 알림 저장
        Notice notice = Notice.builder()
            .receiver(post.getOrganization().getManager())
            .url(reportMember)
            .content(body)
            .type(NoticeType.REPORT)
            .createTime(LocalDateTime.now())
            .build();
        noticeRepository.save(notice);

        //fcm 전송
        fcmUtil.singleFcmSend(post.getOrganization().getManager(), fcmDTO);

        return true;
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

    @Override
    public PostListResponse listView(PostSliceRequest request) {
        return postRepository.getPostList(request);
    }
}
