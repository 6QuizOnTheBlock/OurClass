package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class UpdateClassNameUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(organizationId: Int, name: String) = runCatching{
        organizationRepository.updateOrganization(organizationId, name)
    }
}