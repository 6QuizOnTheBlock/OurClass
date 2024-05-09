package com.sixkids.domain.usecase.challenge

import com.sixkids.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetChallengeSimpleUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: Int) = runCatching {
        challengeRepository.getChallengeSimple(challengeId)
    }
}
