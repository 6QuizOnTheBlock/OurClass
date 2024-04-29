package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.PostRequest;
import com.quiz.ourclass.domain.board.entity.Image;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.board.repository.ImageRepository;
import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.board.repository.TempMember;
import com.quiz.ourclass.domain.board.repository.TempOrg;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.global.dto.ResultResponse;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
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
    private final ImageRepository imageRepository;
    private final TempMember tempMember;
    private final TempOrg tempOrg;
    private final AwsS3ObjectStorage awsS3ObjectStorage;

    @Transactional
    @Override
    public ResultResponse<Long> write(Long classId, MultipartFile file, PostRequest request)
        throws IOException {
        //멤버가 존재하는지 확인
        List<Member> member = tempMember.findAll();
        if (member.isEmpty()) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }

        //멤버가 쿼리 파라미터로 들어온 단체에 속해있는지 확인(classId)
        List<Organization> organization = tempOrg.findAll();
        if (organization.isEmpty()) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //S3 이미지 파일 업로드
        if (file != null && !file.isEmpty()) {
            String url = awsS3ObjectStorage.uploadFile(file);
            if (url.isEmpty()) {
                throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
            }
            imageRepository.save(new Image(file.getOriginalFilename(), url, LocalDateTime.now()));
        }
        //게시글 저장하기
        Post post = new Post(member.getFirst(), organization.getFirst(), request);
        return ResultResponse.success(postRepository.save(post).getId());
    }

    @Transactional
    @Override
    public ResultResponse<Long> modify(Long id, MultipartFile file, PostRequest request)
        throws IOException {
        //수정 할 게시글 조회
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        //게시글에 첨부된 image 파일 존재 여부 확인 후 기존 이미지와 다르면 이미지 재업로드
        if (post.getImage() == null || !post.getImage().getOriginalName().isEmpty()) {
            Image image = post.getImage();
            if (image == null) { //원래 이미지가 없을 때
                if (file != null && !file.isEmpty()) {
                    String url = awsS3ObjectStorage.uploadFile(file);
                    if (url.isEmpty()) {
                        throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
                    }
                    Image addImage = imageRepository.save(
                        new Image(file.getOriginalFilename(), url, LocalDateTime.now()));
                    post.setImage(addImage);
                }
            } else { //원래 이미지가 있을 때
                //원본 이미지와 수정 이미지가 다를 때(업로드)
                if (!image.getOriginalName().equals(file.getOriginalFilename())) {
                    if (!file.isEmpty()) {
                        String url = awsS3ObjectStorage.uploadFile(file);
                        if (url.isEmpty()) {
                            throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
                        }
                        //원본 이미지 삭제
                        awsS3ObjectStorage.deleteFile(image.getPath());

                        //수정사항 적용
                        image.setOriginalName(file.getOriginalFilename());
                        image.setPath(url);
                        image.setCreateTime(LocalDateTime.now());
                        imageRepository.save(image);
                        post.setImage(image);
                    }
                }
            }
        }
        //게시글 정보 변경
        post.setPostCategory(request.getType());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setSecretStatus(request.getAnonymous());

        return ResultResponse.success(postRepository.save(post).getId());
    }

    public void imageUpload() {

    }
}
