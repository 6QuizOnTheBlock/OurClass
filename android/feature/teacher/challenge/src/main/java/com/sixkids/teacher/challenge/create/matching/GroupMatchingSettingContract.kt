package com.sixkids.teacher.challenge.create.matching

import com.sixkids.model.MemberSimple

data class GroupMatchingSettingState(
    val isFriendly: Boolean = false,
    val isUnfriendly: Boolean = false,
    val isRandom: Boolean = false,
    val studentList: List<MemberSimple> = emptyList()
)