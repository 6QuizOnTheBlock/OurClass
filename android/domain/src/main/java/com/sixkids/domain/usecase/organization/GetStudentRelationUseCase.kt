package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetStudentRelationUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(orgId: Long, sourceMemberId: Long, targetMemberId: Long) = runCatching {
        organizationRepository.getStudentRelationDetail(orgId, sourceMemberId, targetMemberId)
    }
}