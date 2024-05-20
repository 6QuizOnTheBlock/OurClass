package com.sixkids.domain.usecase.challenge

import com.sixkids.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetRunningChallengeByStudentUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
){
    suspend operator fun invoke(organizationId: Int) =
        runCatching {
            challengeRepository.getRunningChallengesByStudent(organizationId)
        }
}
