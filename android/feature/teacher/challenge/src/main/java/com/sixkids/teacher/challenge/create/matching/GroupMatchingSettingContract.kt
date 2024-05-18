package com.sixkids.teacher.challenge.create.matching

import androidx.annotation.StringRes
import com.sixkids.model.MemberSimple
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class GroupMatchingSettingState(
    val studentList: List<MemberSimple> = emptyList()
) : UiState

sealed interface GroupMatchingSettingEffect: SideEffect {
    data class HandleException(val it: Throwable, val retryAction: () -> Unit) : GroupMatchingSettingEffect
}

enum class MatchingType(
    @StringRes val textRes: Int
) {
    FRIENDLY(R.string.matching_friendly_type),
    UNFRIENDLY(R.string.matching_unfriendly_type),
    RANDOM(R.string.matching_random_type)
}
