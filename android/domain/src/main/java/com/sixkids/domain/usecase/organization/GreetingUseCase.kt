package com.sixkids.domain.usecase.organization

import com.sixkids.domain.repository.OrganizationRepository
import javax.inject.Inject

class GreetingUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(organizationId: Long, memberId: Long) = runCatching {
        organizationRepository.tagGreeting(organizationId, memberId)
    }
}