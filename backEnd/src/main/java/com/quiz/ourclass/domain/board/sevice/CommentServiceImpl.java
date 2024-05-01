package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.board.mapper.CommentMapper;
import com.quiz.ourclass.domain.board.repository.CommentRepository;
import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserAccessUtil userAccessUtil;
    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public Long write(CommentRequest request) {
        Member commentWriter = userAccessUtil.getMember();
        //게시글 찾기
        Post post = postRepository.findById(request.boardId())
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        //게시글을 작성한 사용자 단체와 댓글 작성자의 단체가 같은지 확인
        boolean isSameOrganization = post.getOrganization().getMemberOrganizations().stream()
            .anyMatch(p -> p.getMember().getId() == commentWriter.getId());
        if (!isSameOrganization) {
            throw new GlobalException(ErrorCode.COMMENT_EDIT_PERMISSION_DENIED);
        }

        //게시글 댓글 저장 (부모 댓글이면 0L로 저장됩니다.)
        Comment comment = commentMapper.CommentRequestTocomment(request);
        comment.setCreateTime(LocalDateTime.now());
        comment.setMember(commentWriter);
        comment.setPost(post);

        return commentRepository.save(comment).getId();
    }
}
