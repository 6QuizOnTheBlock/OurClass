package com.quiz.ourclass.domain.board.sevice;


import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.request.PostSliceRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdatePostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.dto.response.PostListResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    Long postWrite(Long organizationId, MultipartFile file, PostRequest request);

    Long postModify(Long postId, MultipartFile file, UpdatePostRequest request);

    Boolean postDelete(Long postId);

    PostDetailResponse postDetailView(Long postId);

    Boolean postReport(Long postId);

    PostListResponse postListView(PostSliceRequest request);
}
