package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.PostRequest;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.board.repository.TempMember;
import com.quiz.ourclass.domain.board.repository.TempOrg;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.global.dto.ApiResponse;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import java.io.IOException;
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
    private final TempMember tempMember;
    private final TempOrg tempOrg;
    private final AwsS3ObjectStorage awsS3ObjectStorage;

    @Transactional
    @Override
    public ApiResponse<Long> write(Long classId, MultipartFile file, PostRequest request)
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
        if (!file.isEmpty()) {
            if (awsS3ObjectStorage.uploadFile(file).isEmpty()) {
                throw new GlobalException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }
        //게시글 저장하기
        Post post = new Post(member.getFirst(), organization.getFirst(), request);
        return ApiResponse.success(postRepository.save(post).getId());
    }
}

