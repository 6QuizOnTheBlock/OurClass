package com.sixkids.teacher.manageclass.statistics

import com.sixkids.model.ClassSummary
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class StatisticsState(
    val isLoading: Boolean = false,
    val organizationName: String = "",
    val statistics: ClassSummary = ClassSummary()
) : UiState

sealed interface StatisticsEffect : SideEffect{
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : StatisticsEffect
}