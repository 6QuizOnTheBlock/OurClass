package com.sixkids.data.model.response

import com.sixkids.model.RelayQuestion
import java.time.LocalDateTime

data class RunnerResponse(
    val id: Long,
    val turn: Int,
    val member: MemberSimpleResponse,
    val time: LocalDateTime = LocalDateTime.now(),
    val question: String,
    val endStatus: Boolean
)

data class MemberSimpleResponse(
    val id: Long,
    val name: String,
    val photo: String,
)

internal fun RunnerResponse.toModel() = RelayQuestion(
    id = id,
    memberId = member.id,
    memberName = member.name,
    memberPhoto = member.photo,
    time = time,
    question = question,
    turn = turn,
    endStatus = endStatus,
)