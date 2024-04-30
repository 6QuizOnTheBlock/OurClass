package com.sixkids.domain.repository

import com.sixkids.model.Challenge

interface ChallengeRepository {

    suspend fun getChallengeHistory(): List<Challenge>

}
