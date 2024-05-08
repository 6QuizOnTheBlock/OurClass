package com.quiz.ourclass.domain.challenge.scheduler;

import com.quiz.ourclass.domain.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChallengeScheduler {

    private final ChallengeService challengeService;

    @Scheduled(fixedRate = 60000)
    public void ChallengeClosing() {
        challengeService.ChallengeClosing();
    }
}
