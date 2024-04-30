package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.global.dto.ResultResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    ResultResponse<Long> write(Long classId, MultipartFile file, PostRequest request)
        throws IOException;

    ResultResponse<Long> modify(Long id, MultipartFile file, PostRequest request)
        throws IOException;

    PostDetailResponse detailView(Long id);
}
