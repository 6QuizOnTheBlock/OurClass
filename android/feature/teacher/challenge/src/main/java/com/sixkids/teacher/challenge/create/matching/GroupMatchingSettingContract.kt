package com.sixkids.teacher.challenge.create.matching

import androidx.annotation.StringRes
import com.sixkids.model.MemberSimple
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class GroupMatchingSettingState(
    val studentList: List<MemberSimple> = emptyList(),
    val matchingType: MatchingType = MatchingType.FRIENDLY
) : UiState

sealed interface GroupMatchingSettingEffect: SideEffect {
    data class MoveToMatchingSuccessStep(val matchingMemberList: List<Long>, val matchingType: MatchingType): GroupMatchingSettingEffect
    data class ShowSnackbar(val message: String): GroupMatchingSettingEffect
}

enum class MatchingType(
    @StringRes val textRes: Int
) {
    FRIENDLY(R.string.matching_friendly_type),
    UNFRIENDLY(R.string.matching_unfriendly_type),
    RANDOM(R.string.matching_random_type)
}
