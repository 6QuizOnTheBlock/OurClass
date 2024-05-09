package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.organization.dto.request.RelationRequest;
import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberDetailResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationSimpleResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
import java.util.List;

public interface MemberOrgService {

    UpdateExpResponse updateMemberExp(long id, UpdateExpRequest updateExpRequest);

    RelationResponse getMemberRelation(long id, RelationRequest relationRequest);

    MemberDetailResponse getMemberDetail(long id, long memberId);

    List<RelationSimpleResponse> getMemberRelations(long id, long memberId, Long limit);
}
