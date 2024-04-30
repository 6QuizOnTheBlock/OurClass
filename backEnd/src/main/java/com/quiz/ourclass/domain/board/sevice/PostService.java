package com.quiz.ourclass.domain.board.sevice;


import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    Long write(Long classId, MultipartFile file, PostRequest request)
        throws IOException;

    Long modify(Long id, MultipartFile file, PostRequest request)
        throws IOException;

    Boolean delete(Long id);

    PostDetailResponse detailView(Long id);
}
