package com.quiz.ourclass.domain.organization.repository;

import com.quiz.ourclass.domain.organization.dto.response.MemberPlayCountResponse;
import java.util.List;

public interface MemberOrganizationRepositoryQuerydsl {

    List<MemberPlayCountResponse> getChallengeCountByOrganizationId(Long id);

    List<MemberPlayCountResponse> getRelayCountByOrganizationId(Long id);
}
