package com.quiz.ourclass.domain.challenge.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ChallengeRequest(long organizationId,
                               String title,
                               String content,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               int reword,
                               int minCount,
                               List<GroupMatchingRequest> groups) {

}
