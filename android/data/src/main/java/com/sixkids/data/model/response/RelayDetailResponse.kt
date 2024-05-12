package com.sixkids.data.model.response

import com.sixkids.model.RelayDetail

data class RelayDetailResponse(
    val relaySimple: RelayResponse,
    val runners: List<RunnerResponse>
)

internal fun RelayDetailResponse.toModel() = RelayDetail(
    id = relaySimple.id,
    startTime = relaySimple.startTime,
    endTime = relaySimple.endTime,
    lastTurn = relaySimple.lastTurn,
    lastMemberName = relaySimple.lastMemberName,
    runnerList = runners.map { it.toModel() }
)