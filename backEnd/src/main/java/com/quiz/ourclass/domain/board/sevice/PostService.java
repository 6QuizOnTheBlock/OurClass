package com.quiz.ourclass.domain.board.sevice;


import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdatePostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    Long write(Long organizationId, MultipartFile file, PostRequest request);

    Long modify(Long id, MultipartFile file, UpdatePostRequest request);

    Boolean delete(Long id);

    PostDetailResponse detailView(Long id);
}
