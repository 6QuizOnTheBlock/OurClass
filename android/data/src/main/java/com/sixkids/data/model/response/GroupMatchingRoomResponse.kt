package com.sixkids.data.model.response

import com.sixkids.model.MatchingRoom

data class GroupMatchingRoomResponse(
    val dataKey: String,
    val minCount: Int
)

internal fun GroupMatchingRoomResponse.toModel(): MatchingRoom =
    MatchingRoom(
        dataKey = dataKey,
        minCount = minCount
    )
