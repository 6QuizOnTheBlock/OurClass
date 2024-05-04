package com.sixkids.teacher.challenge.create

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChallengeCreateViewModel @Inject constructor(

) : BaseViewModel<ChallengeCreateUiState, ChallengeCreateEffect>(
    ChallengeCreateUiState()
) {


    private var title: String = ""
    private var content: String = ""
    private var startTime: LocalDateTime = LocalDateTime.now()
    private var endTime: LocalDateTime = LocalDateTime.now()
    private var point: String = ""

    fun moveNextStep() {
        intent {
            copy(step = ChallengeCreateStep.GROUP_TYPE)
        }
    }

    fun onShowSnackbar(snackbarToken: SnackbarToken) {
        postSideEffect(ChallengeCreateEffect.ShowSnackbar(snackbarToken))
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun updateStartTime(startTime: LocalDateTime) {
        this.startTime = startTime
    }

    fun updateEndTime(endTime: LocalDateTime) {
        this.endTime = endTime
    }

    fun updatePoint(point: String) {
        this.point = point
    }


}
