package com.sixkids.teacher.challenge.create.info

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState
import java.time.LocalDateTime

data class InfoState(
    val title: String = "",
    val content: String = "",
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val point: String = "",
    val step: InfoStep = InfoStep.TITLE,
    val stepVisibilityList: List<Boolean> = emptyList()
) : UiState

sealed interface InfoEffect : SideEffect {

    data class UpdateTitle(val title: String) : InfoEffect
    data class UpdateContent(val content: String) : InfoEffect
    data class UpdateStartTime(val startTime: LocalDateTime) : InfoEffect
    data class UpdateEndTime(val endTime: LocalDateTime) : InfoEffect
    data class UpdatePoint(val point: String) : InfoEffect
    data object MoveGroupTypeStep : InfoEffect
    data object ShowInputErrorSnackbar : InfoEffect
}

enum class InfoStep {
    TITLE,
    CONTENT,
    START_TIME,
    END_TIME,
    POINT
}
