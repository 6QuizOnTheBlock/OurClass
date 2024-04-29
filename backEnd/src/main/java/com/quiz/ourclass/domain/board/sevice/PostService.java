package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.PostRequest;
import com.quiz.ourclass.global.dto.ApiResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    ApiResponse<Long> write(Long classId, MultipartFile file, PostRequest request)
        throws IOException;
}
