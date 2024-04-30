package com.quiz.ourclass.domain.challenge.service;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.entity.Challenge;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import com.quiz.ourclass.domain.challenge.entity.GroupMember;
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
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ReportRepository reportRepository;
    private final ChallengeMapper challengeMapper;
    private final ChallengeGroupMapper challengeGroupMapper;
    private final ReportMapper reportMapper;
    private final AwsS3ObjectStorage awsS3ObjectStorage;

    @Override
    public ChallengeSliceResponse getChallenges(ChallengSliceRequest challengSliceRequest) {
        return challengeRepository.getChallenges(challengSliceRequest);
    }

    @Transactional
    @Override
    public long createChallenge(ChallengeRequest challengeRequest) {
        Challenge challenge = challengeMapper.challengeRequestToChallenge(challengeRequest);
        Organization organization = organizationRepository.findById(
                challengeRequest.organizationId())
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
        challenge.setOrganization(organization);
        challengeRepository.save(challenge);
        if (!challengeRequest.groups().isEmpty()) {
            challengeRequest.groups().forEach(request -> {
                ChallengeGroup group = challengeGroupMapper.groupMatchingRequestToChallengeGroup(
                    request);
                group.setChallenge(challenge);
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
        //TODO: 유저가 해당 그룹 리더가 맞는지 검사 로직 추가필요
        String fileUrl;
        try {
            fileUrl = awsS3ObjectStorage.uploadFile(file);
        } catch (GlobalException | IOException e) {
            throw new GlobalException(ErrorCode.AWS_SERVER_ERROR);
        }
        Report report = reportMapper.reportRequestToReport(reportRequest);
        ChallengeGroup group = challengeGroupRepository.findById(reportRequest.groupId())
            .orElseThrow(() -> new GlobalException(ErrorCode.CHALLENGE_GROUP_NOT_FOUND));
        report.setFile(fileUrl);
        report.setChallengeGroup(group);
        report.setCreateTime(LocalDateTime.now());
        return reportRepository.save(report).getId();
    }

    @Transactional
    @Override
    public void confirmReport(long id, ReportType reportType) {
        //TODO: 해당 학급 선생권한 체크 필요
        Report report = reportRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.REPORT_NOW_FOUND));
        report.setAcceptStatus(reportType);
        reportRepository.save(report);
    }
}
