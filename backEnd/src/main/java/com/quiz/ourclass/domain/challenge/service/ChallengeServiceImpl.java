package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.ChallengeSimpleDTO;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSimpleResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningMemberChallengeResponse;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.challenge.entity.GroupMember;
import com.quiz.ourclass.domain.challenge.entity.GroupType;
import com.quiz.ourclass.domain.challenge.entity.Report;
import com.quiz.ourclass.domain.challenge.entity.ReportType;
import com.quiz.ourclass.domain.challenge.mapper.ChallengeGroupMapper;
import com.quiz.ourclass.domain.challenge.mapper.ChallengeMapper;
import com.quiz.ourclass.domain.challenge.mapper.ReportMapper;
import com.quiz.ourclass.domain.challenge.repository.ChallengeGroupRepository;
import com.quiz.ourclass.domain.challenge.repository.ChallengeRepository;
import com.quiz.ourclass.domain.challenge.repository.GroupMemberRepository;
import com.quiz.ourclass.domain.challenge.repository.ReportRepository;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import com.quiz.ourclass.global.util.FcmType;
import com.quiz.ourclass.global.util.FcmUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import com.quiz.ourclass.global.util.scheduler.SchedulingService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeGroupRepository challengeGroupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final OrganizationRepository organizationRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final ReportRepository reportRepository;
    private final ChallengeMapper challengeMapper;
    private final ChallengeGroupMapper challengeGroupMapper;
    private final ReportMapper reportMapper;
    private final UserAccessUtil accessUtil;
    private final AwsS3ObjectStorage awsS3ObjectStorage;
    private final FcmUtil fcmUtil;
    private final SchedulingService schedulingService;

    @Override
    public ChallengeSliceResponse getChallenges(ChallengeSliceRequest challengeSliceRequest) {
        return challengeRepository.getChallenges(challengeSliceRequest);
    }

    @Transactional
    @Override
    public long createChallenge(ChallengeRequest challengeRequest) {
        if (challengeRepository.existsByOrganizationIdAndEndStatusIsFalse(
            challengeRequest.organizationId())) {
            throw new GlobalException(ErrorCode.EXIST_RUNNING_CHALLENGE);
        }
        Challenge challenge = challengeMapper.challengeRequestToChallenge(challengeRequest);
        Organization organization = organizationRepository.findById(
                challengeRequest.organizationId())
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        challenge.setOrganization(organization);
        challengeRepository.save(challenge);
        schedulingService.scheduleTask(challenge, this::challengeClosing, challenge.getEndTime());

        if (!challengeRequest.groups().isEmpty()) {
            challengeRequest.groups().forEach(request -> {
                ChallengeGroup group = challengeGroupMapper.groupMatchingRequestToChallengeGroup(
                    request);
                group.setChallenge(challenge);
                group.setGroupType(GroupType.DESIGN);
                challengeGroupRepository.save(group);
                List<Member> members = request.students().stream()
                    .map(memberRepository::findById).filter(Optional::isPresent).map(Optional::get)
                    .toList();
                List<GroupMember> groupMembers = members.stream().map(
                        member -> GroupMember.builder().member(member).challengeGroup(group).build())
                    .toList();
                groupMemberRepository.saveAll(groupMembers);
            });
        }
        return challenge.getId();
    }

    @Transactional
    @Override
    public long createReport(ReportRequest reportRequest, MultipartFile file) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        ChallengeGroup group = challengeGroupRepository.findById(reportRequest.groupId())
            .orElseThrow(() -> new GlobalException(ErrorCode.CHALLENGE_GROUP_NOT_FOUND));
        if (group.getLeaderId() != member.getId()) {
            throw new GlobalException(ErrorCode.NOT_CHALLENGE_GROUP_LEADER);
        }
        if (group.getChallenge().isEndStatus()) {
            throw new GlobalException(ErrorCode.CHALLENGE_IS_END);
        }
        String fileUrl;
        fileUrl = awsS3ObjectStorage.uploadFile(file);
        Organization organization = group.getChallenge().getOrganization();
        String title = fcmUtil.makeFcmTitle(organization.getName(), FcmType.CHALLENGE.name());
        String body = fcmUtil.makeChallengeBody(member.getName(), group.getChallenge().getTitle());
        fcmUtil.singleFcmSend(organization.getManager(), fcmUtil.makeFcmDTO(title, body));
        Report report = reportMapper.reportRequestToReport(reportRequest);
        report.setFile(fileUrl);
        report.setChallengeGroup(group);
        report.setAcceptStatus(ReportType.BEFORE);
        report.setCreateTime(LocalDateTime.now());
        return reportRepository.save(report).getId();
    }

    @Transactional
    @Override
    public void confirmReport(long id, ReportType reportType) {
        Report report = reportRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.REPORT_NOW_FOUND));
        Member loginMember = accessUtil.getMember().orElseThrow();
        Organization organization = report.getChallengeGroup().getChallenge().getOrganization();
        if (accessUtil.isOrganizationManager(loginMember, organization.getId()).isEmpty()) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_MANAGER);
        }
        report.setAcceptStatus(reportType);
        List<Member> members = report.getChallengeGroup().getGroupMembers().stream()
            .map(GroupMember::getMember).toList();
        if (reportType.equals(ReportType.APPROVE)) {
            members.forEach(member -> {
                MemberOrganization memberOrganization = memberOrganizationRepository.findByOrganizationAndMember(
                    organization, member).orElseThrow(
                    () -> new GlobalException(ErrorCode.MEMBER_ORGANIZATION_NOT_FOUND));
                memberOrganization.updateChallengeCount();
                memberOrganization.updateExp(report.getChallengeGroup().getChallenge().getReward());
                memberOrganizationRepository.save(memberOrganization);
            });
        }
        String title = fcmUtil.makeFcmTitle(organization.getName(), FcmType.CHALLENGE.name());
        String body = fcmUtil.makeChallengeConfirmBody(
            report.getChallengeGroup().getChallenge().getTitle(), reportType);
        fcmUtil.multiFcmSend(members, fcmUtil.makeFcmDTO(title, body));
        reportRepository.save(report);
    }

    @Override
    public RunningChallengeResponse getRunningChallenge(long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        Challenge challenge = challengeRepository.findFirstByOrganizationAndEndStatusIsFalse(
                organization)
            .orElseThrow(() -> new GlobalException(ErrorCode.RUNNING_CHALLENGE_NOT_FOUND));
        int waitingCount = challengeGroupRepository.countByChallengeAndCompleteStatusIsFalse(
            challenge);
        ChallengeSimpleDTO challengeSimpleDTO = challengeMapper.challengeToChallengeSimpleDTO(
            challenge);
        return RunningChallengeResponse.builder().challengeSimpleDTO(challengeSimpleDTO)
            .waitingCount(waitingCount).build();
    }

    @Override
    public RunningMemberChallengeResponse getRunningMemberChallenge(long organizationId) {
        Member member = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(organizationId)
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        Challenge challenge = challengeRepository.findFirstByOrganizationAndEndStatusIsFalse(
                organization)
            .orElseThrow(() -> new GlobalException(ErrorCode.RUNNING_CHALLENGE_NOT_FOUND));
        Optional<ChallengeGroup> group = challengeGroupRepository.findDistinctChallengeGroupByChallengeAndGroupMembersMember(
            challenge, member);
        ChallengeSimpleDTO challengeSimpleDTO = challengeMapper.challengeToChallengeSimpleDTO(
            challenge);
        if (group.isEmpty()) {
            return RunningMemberChallengeResponse.builder()
                .challengeSimpleDTO(challengeSimpleDTO)
                .type(GroupType.FREE).build();
        }
        ChallengeGroup challengeGroup = group.get();
        boolean leaderStatus = challengeGroup.getLeaderId() == member.getId();
        return challengeMapper.groupToRunningMember(challengeSimpleDTO, group.get(), leaderStatus);
    }

    @Override
    public ChallengeResponse getChallengeDetail(long id, Long groupId) {
        return challengeRepository.getChallengeDetail(id, groupId);
    }

    @Override
    public ChallengeSimpleResponse getChallengeSimple(long id) {
        Challenge challenge = challengeRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.CHALLENGE_NOT_FOUND));
        return challengeMapper.challengeToChallengeSimpleResponse(challenge);
    }

    protected void challengeClosing(Challenge challenge) {
        challenge.setEndStatus(true);
        challengeRepository.save(challenge);
    }

    @EventListener(ApplicationReadyEvent.class)
    protected void challengeClosingReload() {
        List<Challenge> challenges = challengeRepository.findAllByEndStatusIsFalse();
        challenges.forEach(challenge -> {
            if (challenge.getEndTime().isBefore(LocalDateTime.now())) {
                challengeClosing(challenge);
            } else {
                schedulingService.scheduleTask(challenge, this::challengeClosing,
                    challenge.getEndTime());
            }
        });
    }
}
