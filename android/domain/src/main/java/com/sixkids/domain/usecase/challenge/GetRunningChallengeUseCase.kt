package com.sixkids.domain.usecase.challenge

import com.sixkids.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetRunningChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(organizationId: Int) = runCatching {
        challengeRepository.getRunningChallenge(organizationId)
    }
}
