package com.quiz.ourclass.domain.board.sevice;


import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    Long write(Long classId, MultipartFile file, PostRequest request);

    Long modify(Long id, MultipartFile file, PostRequest request);

    Boolean delete(Long id);

    PostDetailResponse detailView(Long id);
}
