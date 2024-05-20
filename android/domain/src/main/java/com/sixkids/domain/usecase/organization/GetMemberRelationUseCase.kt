package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetMemberRelationUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(orgId: Long, studentId: Long, limit: Int?) = runCatching {
        organizationRepository.getStudentRelation(orgId, studentId, limit)
    }
}