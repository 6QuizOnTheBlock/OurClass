package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.board.mapper.CommentMapper;
import com.quiz.ourclass.domain.board.repository.CommentRepository;
import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
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
    public Long write(Long orgId, CommentRequest request) {
        //댓글 작성자가 입력으로 들어온 단체에 속해있는지 확인
        MemberOrganization commentWriter =
            userAccessUtil.isMemberOfOrganization(userAccessUtil.getMember(), orgId);

        //게시글 찾기
        Post post = postRepository.findById(request.boardId())
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        //게시글을 작성한 사용자 단체와 댓글 작성자의 단체가 같은지 확인
        boolean isSameOrganization = post.getAuthor().getMemberOrganizations().stream()
            .anyMatch(p -> p.getOrganization().getId() == commentWriter.getOrganization().getId());
        if (!isSameOrganization) {
            throw new GlobalException(ErrorCode.COMMENT_EDIT_PERMISSION_DENIED);
        }

        //게시글 댓글 저장 (부모 댓글이면 0L로 저장됩니다.)
        Comment comment = commentMapper.CommentRequestTocomment(request);
        comment.setCreateTime(LocalDateTime.now());
        comment.setMember(commentWriter.getMember());
        comment.setPost(post);

        return commentRepository.save(comment).getId();
    }

    @Override
    public Long modify(Long commentId, CommentRequest request) {
        Member member = userAccessUtil.getMember();

        //댓글 조회
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        //댓글 수정 권한 확인
        if (comment.getMember().getId() != member.getId()) {
            throw new GlobalException(ErrorCode.COMMENT_EDIT_PERMISSION_DENIED);
        }

        //댓글 수정
        commentMapper.updateCommentFromRequest(request, comment);
        return commentRepository.save(comment).getId();
    }
}
