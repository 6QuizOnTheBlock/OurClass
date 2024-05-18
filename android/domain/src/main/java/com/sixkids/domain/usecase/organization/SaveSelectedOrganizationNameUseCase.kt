package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class SaveSelectedOrganizationNameUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(organizationName: String) {
        organizationRepository.saveSelectedOrganizationName(organizationName)
    }
}