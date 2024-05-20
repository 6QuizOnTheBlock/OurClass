package com.quiz.ourclass.domain.chat.service;


import com.quiz.ourclass.domain.chat.entity.ChatRoom;
import com.quiz.ourclass.domain.chat.repository.ChatRoomRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;
    private final UserAccessUtil userAccessUtil;

    @Transactional
    public void createChatRoom(Organization organization) {
        ChatRoom chatRoom = ChatRoom.builder()
            .organization(organization)
            .build();

        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void exitChatRoom(Long roomId) {
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        isMemberAuthorizedToJoinRoom(member.getId(), roomId);

        redisUtil.removeChatRoomUser(roomId, String.valueOf(member.getId()));
    }

    public void isMemberAuthorizedToJoinRoom(Long memberId, Long chatRoomId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getRole() == Role.STUDENT) {
            boolean checkMemberInGroup = chatRoomRepository.findByOrganization_Id(chatRoomId)
                .map(chatRoom -> chatRoom.getOrganization().getMemberOrganizations()
                    .stream()
                    .anyMatch(orgMembers -> orgMembers.getMember().getId() == memberId))
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
            if (!checkMemberInGroup) {
                throw new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION);
            }
        } else if (member.getRole() == Role.TEACHER) {
            userAccessUtil.isOrganizationManager(member, chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_IN_ORGANIZATION));
        }
    }
}

