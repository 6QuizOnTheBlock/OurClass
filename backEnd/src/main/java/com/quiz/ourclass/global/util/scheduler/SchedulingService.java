package com.quiz.ourclass.global.util.scheduler;

import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import com.quiz.ourclass.domain.relay.repository.RelayMemberRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@EnableScheduling
@RequiredArgsConstructor
@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;
    private final TransactionTemplate transactionTemplate;
    private final RelationshipRepository relationshipRepository;
    private final RelayMemberRepository relayMemberRepository;

    public <T> void scheduleTask(T target, Consumer<T> action, LocalDateTime executionTime) {
        SchedulingTask<T> task = new SchedulingTask<>(target, action, transactionTemplate);
        Instant executionDate = executionTime.atZone(ZoneId.systemDefault()).toInstant();

        taskScheduler.schedule(task, executionDate);
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    protected void relationShipScore() {
        List<Relationship> relationshipList =
            relationshipRepository.findAllByOrganizationIdOrderByMember1(1L);

        if (!relationshipList.isEmpty()) {
            relationshipList.stream()
                .map(this::updateRelationShipScore)
                .forEach(relationshipRepository::save);
        }
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