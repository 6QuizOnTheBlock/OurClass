package com.quiz.ourclass.global.util.scheduler;

import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import com.quiz.ourclass.domain.relay.repository.RelayMemberRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;
    private final TransactionTemplate transactionTemplate;
    private final RelationshipRepository relationshipRepository;
    private final RelayMemberRepository relayMemberRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final OrganizationRepository organizationRepository;

    public <T> void scheduleTask(T target, Consumer<T> action, LocalDateTime executionTime) {
        SchedulingTask<T> task = new SchedulingTask<>(target, action, transactionTemplate);
        Instant executionDate = executionTime.atZone(ZoneId.systemDefault()).toInstant();

        taskScheduler.schedule(task, executionDate);
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    protected void relationShipScore() {
        List<Organization> organizations =
            organizationRepository.findAll();

        for (Organization organization : organizations) {
            List<Relationship> relationshipList =
                relationshipRepository.findAllByOrganizationIdOrderByMember1(organization.getId());

            if (!relationshipList.isEmpty()) {
                relationshipList.stream()
                    .map(this::updateRelationShipScore)
                    .forEach(relationshipRepository::save);
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 15 3 * * *")
    protected void isolationScore() {
        List<Organization> organizations = organizationRepository.findAll();

        for (Organization organization : organizations) {
            List<MemberOrganization> memberOrganizations =
                memberOrganizationRepository.findAllByOrganizationOrderByMemberId(organization);
            List<Double> allScores = new ArrayList<>();

            // 전체 점수 수집
            for (MemberOrganization member : memberOrganizations) {
                allScores.addAll(retrieveScores(member, memberOrganizations, organization));
            }

            // 전체 점수의 평균과 표준편차 계산
            double mean = allScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

            double stdDev = calculateStandardDeviation(allScores, mean);

            // 각 멤버의 점수 정규화 및 소외도 계산
            for (MemberOrganization member : memberOrganizations) {
                List<Double> scores = retrieveScores(member, memberOrganizations, organization);

                List<Double> normalizedScores;
                if (stdDev < 1e-10) { // 매우 작은 표준편차 처리
                    // 표준편차가 0일 때, 즉 모든 점수가 같을 때
                    // 모든 정규화 점수를 0으로 설정
                    normalizedScores = Collections.nCopies(scores.size(), 0.0);
                } else { // 정규화
                    normalizedScores = scores.stream()
                        .map(score -> (score - mean) / stdDev)
                        .toList();
                }
                // 소외도 점수 도출
                double isolationScore = normalizedScores.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

                updateIsolationScore(member, isolationScore);
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 30 3 * * *")
    public void isolationRank() {
        List<Organization> organizations = organizationRepository.findAll();

        for (Organization organization : organizations) {
            // isolationPoint 기준으로 내림차순 정렬
            List<MemberOrganization> memberOrganizations = memberOrganizationRepository
                .findAllByOrganizationOrderByMemberId(organization)
                .stream()
                .sorted(
                    Comparator.comparingDouble(MemberOrganization::getIsolationPoint).reversed()
                )
                .toList();

            // 순위 계산 및 업데이트
            int rank = 1; // 현재 순위
            int actualRank = 1; // 다음에 적용될 실제 순위
            double lastPoint = Double.MAX_VALUE; // 이전 isolationPoint
            for (MemberOrganization memberOrg : memberOrganizations) {
                // 점수가 변경되면 순위 업데이트
                if (memberOrg.getIsolationPoint() != lastPoint) {
                    lastPoint = memberOrg.getIsolationPoint();
                    rank = actualRank;
                }
                memberOrg.updateIsolationRank(rank);
                memberOrganizationRepository.save(memberOrg);

                // 실제 사용될 다음 순위 업데이트 (동 순위 고려)
                actualRank++;
            }
        }
    }

    private List<Double> retrieveScores(
        MemberOrganization member, List<MemberOrganization> memberOrganizations,
        Organization organization
    ) {
        List<Double> scores = new ArrayList<>();
        for (MemberOrganization otherMember : memberOrganizations) {
            if (!member.equals(otherMember)) {
                relationshipRepository.findByOrganizationIdAndMember1IdAndMember2Id(
                    organization.getId(),
                    member.getMember().getId(),
                    otherMember.getMember().getId()
                ).ifPresent(rel -> scores.add(rel.getRelationPoint()));
            }
        }
        return scores;
    }

    private double calculateStandardDeviation(List<Double> scores, double mean) {
        double variance = scores.stream()
            .mapToDouble(score -> Math.pow(score - mean, 2))
            .average()
            .orElse(0.0);
        return Math.sqrt(variance);
    }

    private void updateIsolationScore(MemberOrganization member, double isolationScore) {
        member.updateIsolationPoint(isolationScore);
        memberOrganizationRepository.save(member);
    }

    private Relationship updateRelationShipScore(Relationship relationship) {
        double newScore = calculateScore(relationship);
        relationship.updateRelationPoint(newScore);
        return relationship;
    }

    private double calculateScore(Relationship relationship) {
        //TODO : relayCount 비정규화해서 가져오기
        int sendCount = relayMemberRepository.countByCurMemberIdAndNextMemberId(
            relationship.getMember1().getId(), relationship.getMember2().getId()
        );
        int receiveCount = relayMemberRepository.countByCurMemberIdAndNextMemberId(
            relationship.getMember1().getId(), relationship.getMember2().getId()
        );

        return relationship.getFreeGroupCount() * 1.5
            + relationship.getSocialCount() * 1.0
            + relationship.getTagGreetingCount() * 0.75
//            + relationship.getRelayCount() * 0.5
            + (sendCount + receiveCount) * 0.5
            + relationship.getDesignGroupCount() * 0.25;
    }
}