package com.sixkids.domain.usecase.challenge

import com.sixkids.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetChallengeDetailUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: Long, groupId: Long?) = runCatching {
        challengeRepository.getChallengeDetail(challengeId, groupId)
    }
}
