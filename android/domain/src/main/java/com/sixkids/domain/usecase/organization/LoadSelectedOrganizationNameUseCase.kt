package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class LoadSelectedOrganizationNameUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(): String {
        return organizationRepository.loadSelectedOrganizationName()
    }
}