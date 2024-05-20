package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetOrganizationMemberUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(orgId: Int) = runCatching {
        organizationRepository.getOrganizationMembers(orgId)
    }
}