package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdateCommentRequest;
import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.board.mapper.CommentMapper;
import com.quiz.ourclass.domain.board.repository.CommentRepository;
import com.quiz.ourclass.domain.board.repository.PostRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.notice.entity.Notice;
import com.quiz.ourclass.domain.notice.entity.NoticeType;
import com.quiz.ourclass.domain.notice.repository.NoticeRepository;
import com.quiz.ourclass.global.dto.FcmDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.FcmType;
import com.quiz.ourclass.global.util.FcmUtil;
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
    private final NoticeRepository noticeRepository;
    private final UserAccessUtil userAccessUtil;
    private final FcmUtil fcmUtil;
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

    @Override
    public Long modify(Long commentId, UpdateCommentRequest request) {
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
        comment.setUpdateTime(LocalDateTime.now());
        return commentRepository.save(comment).getId();
    }

    /*
     * 시스템 관리자는 모든 댓글 삭제 가능
     * 단체 담당자는 해당 단체의 댓글 삭제 가능
     * 작성자 본인은 본인의 댓글 삭제 가능
     */
    @Override
    public Boolean delete(Long commentId) {
        Member member = userAccessUtil.getMember();

        //댓글 삭제 권한 검증
        Role requesterRole = member.getRole();
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
        if (requesterRole == Role.STUDENT) {
            if (comment.getMember().getId() != member.getId()) {
                throw new GlobalException(ErrorCode.COMMENT_DELETE_STUDENT_PERMISSION_DENIED);
            }
        } else if (requesterRole == Role.TEACHER) {
            Long orgId = comment.getPost().getOrganization().getId();
            userAccessUtil.isMemberOfOrganization(member, orgId);
        }
        //댓글 삭제
        commentRepository.delete(comment);
        return true;
    }

    @Transactional
    @Override
    public Boolean report(Long commentId) {
        Member member = userAccessUtil.getMember();

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        userAccessUtil.isMemberOfOrganization(member, comment.getPost().getOrganization().getId());

        String reportMember = member.getName();
        String authorMember = comment.getPost().getAuthor().getName();
        String title = fcmUtil.makeReportTitle(
            comment.getPost().getOrganization().getName(), FcmType.COMMENT.getType()
        );
        String body = fcmUtil.makeReportBody(
            authorMember, reportMember, FcmType.COMMENT.getType()
        );

        FcmDTO fcmDTO = fcmUtil.makeFcmDTO(title, body);

        // 알림 저장
        Notice notice = Notice.builder()
            .receiver(comment.getPost().getOrganization().getManager())
            .url(reportMember)
            .content(body)
            .type(NoticeType.REPORT)
            .createTime(LocalDateTime.now())
            .build();
        noticeRepository.save(notice);

        //fcm 전송
        fcmUtil.singleFcmSend(comment.getPost().getOrganization().getManager(), fcmDTO);

        return true;
    }
}
