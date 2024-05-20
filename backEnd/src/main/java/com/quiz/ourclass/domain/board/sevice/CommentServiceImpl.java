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
import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
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
    private final RelationshipRepository relationshipRepository;
    private final UserAccessUtil userAccessUtil;
    private final FcmUtil fcmUtil;
    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public Long write(CommentRequest request) {
        Member commentWriter = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        //게시글 찾기
        Post post = postRepository.findById(request.boardId())
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        long orgId = post.getOrganization().getId();

        //게시글을 작성한 사용자 단체와 댓글 작성자의 단체가 같은지 확인
        if (commentWriter.getRole() == Role.STUDENT) {
            boolean isSameOrganization = post.getOrganization().getMemberOrganizations().stream()
                .anyMatch(p -> p.getMember().getId() == commentWriter.getId());

            if (!isSameOrganization) {
                throw new GlobalException(ErrorCode.COMMENT_EDIT_PERMISSION_DENIED);
            }
        } else if (commentWriter.getRole() == Role.TEACHER) {
            userAccessUtil.isOrganizationManager(commentWriter, post.getOrganization().getId())
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));
        }

        if (post.getAuthor().getId() != commentWriter.getId() &&
            post.getAuthor().getRole() == Role.STUDENT) {
            //해당 게시글에 반응(댓글 작성)을 하였는지 확인
            boolean isFirstComment = request.parentId() == 0L
                && !commentRepository.existsByPostAndMember(post, commentWriter);
            if (isFirstComment) {
                Member member1 = post.getAuthor().getId() < commentWriter.getId() ?
                    post.getAuthor() : commentWriter;
                Member member2 = post.getAuthor().getId() < commentWriter.getId() ?
                    commentWriter : post.getAuthor();

                updateSocialCount(orgId, member1, member2);
            }
            //해당 댓글에 반응(대댓글 작성)을 하였는지 확인
            if (request.parentId() > 0L) {
                Comment parentComment = commentRepository.findById(request.parentId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
                checkAndUpdateFirstResponse(post, commentWriter, parentComment);
            }
        }

        //게시글 댓글 저장 (부모 댓글이면 0L로 저장됩니다.)
        Comment comment = commentMapper.CommentRequestTocomment(request);
        comment.setCreateTime(LocalDateTime.now());
        comment.setMember(commentWriter);
        comment.setPost(post);

        return commentRepository.save(comment).getId();
    }

    @Transactional
    @Override
    public Long modify(Long commentId, UpdateCommentRequest request) {
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

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
    @Transactional
    @Override
    public Boolean delete(Long commentId) {
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

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
            userAccessUtil.isOrganizationManager(member, orgId)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));
        }

        //자식 댓글 삭제하기
        commentRepository.deleteByParentId(comment.getId());

        //댓글 삭제
        commentRepository.delete(comment);
        return true;
    }

    @Transactional
    @Override
    public Boolean report(Long commentId) {
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getRole() == Role.TEACHER) {
            throw new GlobalException(ErrorCode.TEACHER_CAN_NOT_REPORT);
        }

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        userAccessUtil.isMemberOfOrganization(member, comment.getPost().getOrganization().getId())
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));

        String reportMember = member.getName();
        String authorMember = comment.getPost().getAuthor().getName();
        String title = fcmUtil.makeFcmTitle(
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

    /*
     * 부모 댓글 작성자가 게시글 작성자일 때
     * 대댓글 작성자가 게시글에 댓글로 반응을 한 적이 없을 때
     *
     * 부모 댓글 작성자가 게시글 작성자랑 다를 떄
     * 대댓글 작성자가 부모 댓글 작성자에게 반응을 하지 않았을 때
     */
    private void checkAndUpdateFirstResponse(
        Post post, Member commentWriter, Comment parentComment) {
        if ((post.getAuthor().getId() == parentComment.getMember().getId() &&
            !commentRepository.existsByPostAndMember(post, commentWriter))
            ||
            (post.getAuthor().getId() != parentComment.getMember().getId() &&
                !commentRepository.existsByParentIdAndMember(parentComment.getId(),
                    commentWriter))) {
            long orgId = post.getOrganization().getId();

            Member member1 = post.getAuthor().getId() < commentWriter.getId() ?
                post.getAuthor() : commentWriter;
            Member member2 = post.getAuthor().getId() < commentWriter.getId() ?
                commentWriter : post.getAuthor();

            updateSocialCount(orgId, member1, member2);
        }
    }

    private void updateSocialCount(Long orgId, Member member1, Member member2) {
        Relationship relationship =
            relationshipRepository.findByOrganizationIdAndMember1IdAndMember2Id(
                orgId, member1.getId(), member2.getId()
            ).orElseThrow(() -> new GlobalException(ErrorCode.RELATION_NOT_FOUND));

        relationship.updateSocialCount();
    }
}
