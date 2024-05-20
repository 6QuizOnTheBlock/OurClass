package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class JoinOrganizationUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(orgId: Int, code: String) = runCatching {
        organizationRepository.joinOrganization(orgId, code)
    }
}