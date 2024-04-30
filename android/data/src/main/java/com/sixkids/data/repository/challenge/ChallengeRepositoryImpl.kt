package com.sixkids.data.repository.challenge

import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.model.Challenge
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(

) : ChallengeRepository {
    override suspend fun getChallengeHistory(): List<Challenge> {
        TODO("Not yet implemented")
    }

}
