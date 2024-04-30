package com.sixkids.data.model.response

import com.sixkids.model.Challenge

data class ChallengeHistoryResponse(
    val page: Int,
    val size: Int,
    val last: Boolean,
    val challenges : List<Challenge>
)
