package com.sixkids.teacher.challenge.create.info

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState
import java.time.LocalDateTime

data class InfoState(
    val title: String = "",
    val content: String = "",
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val point: Int = 0,
    val step: InfoStep = InfoStep.TITLE,
) : UiState

sealed interface InfoEffect : SideEffect {

    data class UpdateTitle(val title: String) : InfoEffect
    data class UpdateContent(val content: String) : InfoEffect
    data class UpdateStartTime(val startTime: LocalDateTime) : InfoEffect
    data class UpdateEndTime(val endTime: LocalDateTime) : InfoEffect
    data class UpdatePoint(val point: Int) : InfoEffect
    data object ShowKeyboard : InfoEffect
    data object ShowSnackbar : InfoEffect
}

enum class InfoStep {
    TITLE,
    CONTENT,
    START_TIME,
    END_TIME,
    POINT,
}
