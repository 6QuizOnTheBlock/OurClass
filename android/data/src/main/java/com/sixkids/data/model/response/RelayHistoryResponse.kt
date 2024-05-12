package com.sixkids.data.model.response

import com.sixkids.model.Relay
import java.time.LocalDateTime

data class RelayHistoryResponse(
    val page: Int,
    val size: Int,
    val last: Boolean,
    val relays: List<RelayResponse>
)

data class RelayResponse(
    val id : Long,
    val startTime : LocalDateTime = LocalDateTime.now(),
    val endTime : LocalDateTime = LocalDateTime.now(),
    val lastTurn : Int,
    val lastMemberName : String
)

internal fun RelayResponse.toModel() = Relay(
    id = id,
    startTime = startTime,
    endTime = endTime,
    lastTurn = lastTurn,
    lastMemberName = lastMemberName
)
