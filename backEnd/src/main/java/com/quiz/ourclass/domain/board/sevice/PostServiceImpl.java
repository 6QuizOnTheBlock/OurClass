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
import org.apache.commons.codec.digest.DigestUtils;
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

        // 이미지 처리
        Image image = post.getImage();
        if (file != null && !file.isEmpty()) {
            //file hash 값 계산
            String fileHash = DigestUtils.sha256Hex(file.getInputStream());
            boolean shouldUpdateImage = (image == null || !image.getHash().equals(fileHash));
            if (shouldUpdateImage) { //재 업로드 해야하는 경우
                //기존 이미지가 있으면 삭제
                if (image != null) {
                    awsS3ObjectStorage.deleteFile(image.getPath());
                }
                //이미지 업로드
                String url = awsS3ObjectStorage.uploadFile(file);
                if (url.isEmpty()) {
                    throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
                }
                //이미지 정보 업데이트 또는 새 이미지 저장
                if (image == null) {
                    image = new Image();
                }
                image.setOriginalName(file.getOriginalFilename());
                image.setPath(url);
                image.setCreateTime(LocalDateTime.now());
                image.setHash(fileHash);
                imageRepository.save(image);

                post.setImage(image);
            }
        } else { //file 없을 때 기존에 원본 이미지 삭제
            awsS3ObjectStorage.deleteFile(image.getPath());
            post.setImage(null);
            imageRepository.delete(image);
        }
        // 게시글 정보 변경
        post.setPostCategory(request.getType());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setSecretStatus(request.getAnonymous());

        return ResultResponse.success(postRepository.save(post).getId());
    }

    public void imageUpload() {

    }
}
