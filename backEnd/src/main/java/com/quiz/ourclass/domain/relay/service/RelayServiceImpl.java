package com.quiz.ourclass.domain.relay.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.relay.dto.request.ReceiveRelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.ReceiveRelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;
import com.quiz.ourclass.domain.relay.dto.response.RunningRelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.SendRelayResponse;
import com.quiz.ourclass.domain.relay.entity.Relay;
import com.quiz.ourclass.domain.relay.entity.RelayMember;
import com.quiz.ourclass.domain.relay.repository.RelayMemberRepository;
import com.quiz.ourclass.domain.relay.repository.RelayRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.ConstantUtil;
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
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final UserAccessUtil accessUtil;


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
            .timeout(ConstantUtil.RELAY_TIMEOUT_DAY)
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
        relay.setLastRunner(relayMember);
        relayMemberRepository.save(relayMember);
        MemberOrganization memberOrganization = memberOrganizationRepository.findByOrganizationAndMember(
                organization, member)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_ORGANIZATION_NOT_FOUND));
        memberOrganization.updateRelayCount();
        memberOrganizationRepository.save(memberOrganization);
        return relay.getId();
    }

    @Override
    public RelaySliceResponse getRelays(RelaySliceRequest relaySliceRequest) {
        return relayRepository.getRelays(relaySliceRequest);
    }

    @Override
    public RelayResponse getRelayDetail(long id) {
        return relayRepository.getRelayDetail(id);
    }

    @Override
    public RunningRelayResponse getRunningRelay(long organizationId) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        Relay relay = relayRepository.findByOrganizationIdAndEndStatusIsFalse(organizationId)
            .orElseThrow(() -> new GlobalException(ErrorCode.RUNNING_RELAY_NOT_FOUND));
        boolean isMyTurn = relay.getCurrentRunner().getCurMember().equals(member);
        return RunningRelayResponse.builder()
            .id(relay.getId())
            .currentTurn(relay.getCurrentRunner().getTurn())
            .totalTurn(relay.getTotalCount())
            .currentMemberName(relay.getCurrentRunner().getCurMember().getName())
            .startTime(relay.getStartRunner().getReceiveTime())
            .myTurnStatus(isMyTurn)
            .build();
    }

    @Transactional
    @Override
    public ReceiveRelayResponse receiveRelay(long id, ReceiveRelayRequest receiveRelayRequest) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        Relay relay = relayRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.RELAY_NOT_FOUND));
        RelayMember currentRunner = relay.getCurrentRunner();
        currentRunner.setNextMember(member);
        int turn = currentRunner.getTurn() + 1;
        boolean endStatus = turn == relay.getTotalCount();
        RelayMember relayMember = RelayMember.builder()
            .relay(relay)
            .curMember(member)
            .question(receiveRelayRequest.question())
            .turn(turn)
            .receiveTime(LocalDateTime.now())
            .endStatus(endStatus)
            .build();
        relayMemberRepository.save(relayMember);
        relay.setCurrentRunner(relayMember);
        relay.setLastRunner(relayMember);
        if (endStatus) {
            relay.setEndStatus(true);
            MemberOrganization memberOrganization = memberOrganizationRepository.findByOrganizationAndMember(
                    relay.getOrganization(), member)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_ORGANIZATION_NOT_FOUND));
            memberOrganization.updateExp(ConstantUtil.RELAY_DEMERIT);
        }
        MemberOrganization memberOrganization = memberOrganizationRepository.findByOrganizationAndMember(
                relay.getOrganization(), member)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_ORGANIZATION_NOT_FOUND));
        memberOrganization.updateRelayCount();
        memberOrganizationRepository.save(memberOrganization);
        //TODO: 타임아웃 스케줄러 체크
        return ReceiveRelayResponse.builder()
            .senderName(currentRunner.getCurMember().getName())
            .question(currentRunner.getQuestion())
            .lastStatus(endStatus)
            .demerit(ConstantUtil.RELAY_DEMERIT)
            .build();
    }

    @Override
    public SendRelayResponse sendRelay(long id) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        Relay relay = relayRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.RELAY_NOT_FOUND));
        RelayMember prevRelayMember = relayMemberRepository.findByRelayAndNextMember(relay, member)
            .orElseThrow(() -> new GlobalException(ErrorCode.RELAY_MEMBER_NOT_FOUND));
        MemberOrganization memberOrganization = memberOrganizationRepository.findByOrganizationAndMember(
            relay.getOrganization(), member).orElseThrow();
        memberOrganization.updateExp(ConstantUtil.RELAY_REWARD);
        return SendRelayResponse.builder()
            .prevMemberName(prevRelayMember.getCurMember().getName())
            .prevQuestion(prevRelayMember.getQuestion()).build();
    }
}
