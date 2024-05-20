package com.sixkids.teacher.challenge.create.grouptype

import androidx.annotation.StringRes
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class GroupTypeState(
    val minCount: String = "",
    val groutType: GroupType = GroupType.FREE,
    val groutTypeVisibility: Boolean = false,
) : UiState

sealed interface GroupTypeEffect : SideEffect {

    data class UpdateMinCount(val minCount: String) : GroupTypeEffect
    data class UpdateGroupType(val type: GroupType) : GroupTypeEffect
    data object MoveToMatchingStep : GroupTypeEffect
    data object CreateChallenge : GroupTypeEffect
    data object ShowInputErrorSnackbar : GroupTypeEffect
}

enum class GroupType(
    @StringRes val textRes: Int
) {
    FREE(R.string.free_group),
    GROUP(R.string.matching_group),
}
