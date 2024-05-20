package com.sixkids.teacher.challenge.create.info

import androidx.annotation.StringRes
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class InfoState(
    val title: String = "",
    val content: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now().plusMinutes(5),
    val endTime: LocalTime = LocalTime.now().plusMinutes(5),
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
    data class ShowInputErrorSnackbar(@StringRes val messageRes: Int) : InfoEffect
}

enum class InfoStep {
    TITLE,
    CONTENT,
    START_TIME,
    END_TIME,
    POINT
}
