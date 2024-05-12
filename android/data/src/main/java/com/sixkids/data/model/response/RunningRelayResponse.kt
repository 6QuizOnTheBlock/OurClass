package com.sixkids.data.model.response

import com.sixkids.model.RunningRelay
import java.time.LocalDateTime

data class RunningRelayResponse(
    val id: Long,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val currentMemberName : String,
    val currentTurn : Int,
    val totalTurn : Int,
    val myTurnStatus : Boolean
)

internal fun RunningRelayResponse.toModel() = RunningRelay(
    id = id,
    startTime = startTime,
    curMemberNickname = currentMemberName,
    doneMemberCount = currentTurn,
    totalMemberCount = totalTurn,
    myTurnStatus = myTurnStatus
)