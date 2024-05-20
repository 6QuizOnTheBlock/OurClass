package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetOrganizaionInviteCodeUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(organizationId: Int) = runCatching{
        organizationRepository.getOrganizationInviteCode(organizationId)
    }
}