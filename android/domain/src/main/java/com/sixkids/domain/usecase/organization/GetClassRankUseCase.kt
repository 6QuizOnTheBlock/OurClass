package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetClassRankUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(organizationId: Int) = runCatching {
        organizationRepository.getOrganizationRank(organizationId)
    }
}