package com.sixkids.data.model.response

import com.sixkids.model.Organization
import java.time.LocalDate

data class OrganizationResponse(
    val id: Int,
    val name: String,
    val memberCount: Int,
    val createTime: LocalDate = LocalDate.now(),
)

internal fun OrganizationResponse.toModel() = Organization(
    id = id,
    name = name,
    memberCount = memberCount
)