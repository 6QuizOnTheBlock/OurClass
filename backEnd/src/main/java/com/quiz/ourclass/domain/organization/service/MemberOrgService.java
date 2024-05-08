package com.quiz.ourclass.domain.organization.service;

import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;

public interface MemberOrgService {

    UpdateExpResponse updateMemberExp(long id, UpdateExpRequest updateExpRequest);
}
