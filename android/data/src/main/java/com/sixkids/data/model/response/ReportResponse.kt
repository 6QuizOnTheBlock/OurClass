package com.sixkids.data.model.response

import com.sixkids.model.AcceptStatus
import com.sixkids.model.Report
import java.time.LocalDateTime

data class ReportResponse(
    val id: Long,
    val group: GroupResponse,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val file: String,
    val content: String,
    val acceptStatus: String,
)

internal fun ReportResponse.toModel() = Report(
    id = id,
    group = group.toModel(),
    startTime = startTime,
    endTime = endTime,
    file = file,
    content = content,
    acceptStatus = AcceptStatus.valueOf(acceptStatus),
)
