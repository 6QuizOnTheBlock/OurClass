package com.quiz.ourclass.domain.challenge.dto;

import java.util.List;

public record GroupMatchingRequest(int headCount,
                                   long leaderId,
                                   List<Long> students) {

}
