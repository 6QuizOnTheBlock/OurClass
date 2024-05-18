package com.sixkids.teacher.challenge.create.matching

import androidx.annotation.StringRes
import com.sixkids.model.MemberSimple
import com.sixkids.teacher.challenge.R

data class GroupMatchingSettingState(
    val studentList: List<MemberSimple> = emptyList()
)

enum class MatchingType(
    @StringRes val textRes: Int
) {
    FRIENDLY(R.string.matching_friendly_type),
    UNFRIENDLY(R.string.matching_unfriendly_type),
    RANDOM(R.string.matching_random_type)
}
