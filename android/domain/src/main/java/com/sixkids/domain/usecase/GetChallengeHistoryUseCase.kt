package com.sixkids.domain.usecase

import com.sixkids.domain.repository.ChallengeRepository
import javax.inject.Inject

class GetChallengeHistoryUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
){

}
