package com.sixkids.teacher.challenge.create

import com.sixkids.teacher.challenge.create.grouptype.GroupType
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
    private var headCount : String = ""
    private var groupType: GroupType = GroupType.FREE

    fun moveNextStep() {
        intent {
            when(step) {
                ChallengeCreateStep.INFO -> copy(step = ChallengeCreateStep.GROUP_TYPE)
                ChallengeCreateStep.GROUP_TYPE -> copy(step = ChallengeCreateStep.MATCHING_TYPE)
                ChallengeCreateStep.MATCHING_TYPE -> copy(step = ChallengeCreateStep.CREATE)
                ChallengeCreateStep.CREATE -> copy(step = ChallengeCreateStep.RESULT)
                ChallengeCreateStep.RESULT -> copy(step = ChallengeCreateStep.INFO)
            }
        }
    }

    fun moveToResult() {
        intent {
            copy(step = ChallengeCreateStep.RESULT)
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

    fun updateCount(count: String) {
        this.headCount = count
    }

    fun updateGroupType(groupType: GroupType) {
        this.groupType = groupType
    }


}
