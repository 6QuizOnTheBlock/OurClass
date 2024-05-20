package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetStudentDetailUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(orgId: Long, studentId: Long) = runCatching {
        organizationRepository.getStudentDetail(orgId, studentId)
    }

}