package com.quiz.ourclass.domain.relay.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.relay.dto.request.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;
import com.quiz.ourclass.domain.relay.entity.Relay;
import com.quiz.ourclass.domain.relay.entity.RelayMember;
import com.quiz.ourclass.domain.relay.repository.RelayMemberRepository;
import com.quiz.ourclass.domain.relay.repository.RelayRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RelayServiceImpl implements RelayService {

    private final RelayRepository relayRepository;
    private final RelayMemberRepository relayMemberRepository;
    private final OrganizationRepository organizationRepository;
    private final UserAccessUtil accessUtil;
    private final static Long RELAY_TIMEOUT_DAY = 1L;

    @Transactional
    @Override
    public long createRelay(RelayRequest relayRequest) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(relayRequest.organizationId())
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        int randomCount = organization.getMemberCount() / 2;
        int totalCount = (int) (Math.random() * (randomCount + 1) + randomCount);
        if (relayRepository.existsByOrganizationAndEndStatusIsFalse(organization)) {
            throw new GlobalException(ErrorCode.EXIST_PROGRESS_RELAY);
        }
        Relay relay = Relay.builder()
            .organization(organization)
            .totalCount(totalCount)
            .timeout(RELAY_TIMEOUT_DAY)
            .build();
        relayRepository.save(relay);
        RelayMember relayMember = RelayMember.builder()
            .relay(relay)
            .curMember(member)
            .question(relayRequest.question())
            .turn(1)
            .receiveTime(LocalDateTime.now())
            .build();
        relay.setCurrentRunner(relayMember);
        relay.setStartRunner(relayMember);
        relayMemberRepository.save(relayMember);
        return relay.getId();
    }

    @Override
    public RelaySliceResponse getRelays(RelaySliceRequest relaySliceRequest) {
        return relayRepository.getRelays(relaySliceRequest);
    }
}
