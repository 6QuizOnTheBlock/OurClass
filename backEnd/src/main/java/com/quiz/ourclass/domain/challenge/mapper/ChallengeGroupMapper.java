package com.quiz.ourclass.domain.challenge.mapper;

import com.quiz.ourclass.domain.challenge.dto.request.GroupMatchingRequest;
import com.quiz.ourclass.domain.challenge.entity.ChallengeGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChallengeGroupMapper {

    ChallengeGroup groupMatchingRequestToChallengeGroup(GroupMatchingRequest groupMatchingRequest);
}
