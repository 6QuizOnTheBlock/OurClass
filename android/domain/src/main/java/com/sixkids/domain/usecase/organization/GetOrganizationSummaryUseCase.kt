package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetOrganizationSummaryUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(organizationId: Int) = runCatching {
        organizationRepository.getOrganizationSummary(organizationId)
    }
}