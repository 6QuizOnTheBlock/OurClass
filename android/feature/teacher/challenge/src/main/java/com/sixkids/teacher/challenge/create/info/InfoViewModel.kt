package com.sixkids.teacher.challenge.create.info

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(

) : BaseViewModel<InfoState, InfoEffect>(
    InfoState()
) {
    fun updateTitle(title: String) {
        intent {
            postSideEffect(InfoEffect.UpdateTitle(title))
            copy(title = title)
        }
    }

    fun updateContent(content: String) {
        intent {
            postSideEffect(InfoEffect.UpdateContent(content))
            copy(content = content)
        }
    }

    fun updatePoint(point: String) {
        intent {
            postSideEffect(InfoEffect.UpdatePoint(point))
            copy(point = point)
        }
    }


    fun updateStartDate(startDate: LocalDate) {
        intent {
            val selectedTime = LocalDateTime.of(startDate, startTime)
            postSideEffect(InfoEffect.UpdateStartTime(selectedTime))
            copy(startDate = startDate)
        }
    }

    fun updateEndDate(endDate: LocalDate) {
        intent {
            val selectedTime = LocalDateTime.of(endDate, endTime)
            postSideEffect(InfoEffect.UpdateEndTime(selectedTime))
            copy(endDate = endDate)
        }
    }

    fun updateStartTime(startTime: LocalTime) {
        intent {
            val selectedTime = LocalDateTime.of(startDate, startTime)
            postSideEffect(InfoEffect.UpdateStartTime(selectedTime))
            copy(startTime = startTime)
        }
    }

    fun updateEndTime(endTime: LocalTime) {
        intent {
            val selectedTime = LocalDateTime.of(endDate, endTime)
            postSideEffect(InfoEffect.UpdateEndTime(selectedTime))
            copy(endTime = endTime)
        }
    }

    fun moveNextInput() {
        when (uiState.value.step) {
            InfoStep.TITLE -> {
                intent {
                    copy(
                        step = InfoStep.CONTENT,
                        stepVisibilityList = uiState.value.stepVisibilityList.toMutableList()
                            .apply {
                                this[InfoStep.CONTENT.ordinal] = true
                            })
                }
            }

            InfoStep.CONTENT -> {
                intent {
                    copy(
                        step = InfoStep.START_TIME,
                        stepVisibilityList = uiState.value.stepVisibilityList.toMutableList()
                            .apply {
                                this[InfoStep.START_TIME.ordinal] = true
                            }
                    )
                }
            }

            InfoStep.START_TIME -> {
                intent {
                    copy(
                        step = InfoStep.END_TIME,
                        stepVisibilityList = uiState.value.stepVisibilityList.toMutableList()
                            .apply {
                                this[InfoStep.END_TIME.ordinal] = true
                            })
                }
            }

            InfoStep.END_TIME -> {
                intent {
                    copy(
                        step = InfoStep.POINT,
                        stepVisibilityList = uiState.value.stepVisibilityList.toMutableList()
                            .apply {
                                this[InfoStep.POINT.ordinal] = true
                            })
                }
            }

            InfoStep.POINT -> {
                postSideEffect(InfoEffect.MoveGroupTypeStep)
            }

        }
    }

    fun setInitVisibility() {
        if (uiState.value.stepVisibilityList.isNotEmpty()) return
        intent {
            copy(
                stepVisibilityList = MutableList(InfoStep.entries.size) { false }.apply {
                    this[InfoStep.TITLE.ordinal] = true
                }
            )
        }
    }

    fun moveNextStep() {
        if (
            uiState.value.title.isEmpty() ||
            uiState.value.content.isEmpty() ||
            uiState.value.point.isEmpty()
        ) {
            postSideEffect(InfoEffect.ShowInputErrorSnackbar)
        } else {
            postSideEffect(InfoEffect.MoveGroupTypeStep)
        }
    }

    fun focusChange(infoStep: InfoStep) {
        intent {
            copy(step = infoStep)
        }
    }

}
