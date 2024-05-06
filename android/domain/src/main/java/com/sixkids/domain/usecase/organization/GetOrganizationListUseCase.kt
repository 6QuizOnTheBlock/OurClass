package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetOrganizationListUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke() = runCatching {
        organizationRepository.getClassList()
    }
}