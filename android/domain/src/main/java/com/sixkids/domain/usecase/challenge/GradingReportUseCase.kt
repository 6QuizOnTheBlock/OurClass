package com.sixkids.domain.usecase.challenge

import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.model.AcceptStatus
import javax.inject.Inject

class GradingReportUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(reportId: Long, acceptStatus: AcceptStatus) =
        runCatching {
            challengeRepository.gradingChallenge(
                reportId, acceptStatus
            )
        }
}
