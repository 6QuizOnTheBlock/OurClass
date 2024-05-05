package com.sixkids.teacher.challenge.create.grouptype

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupTypeViewModel @Inject constructor(

) : BaseViewModel<GroupTypeState, GroupTypeEffect>(
    GroupTypeState()
) {
    fun createChallenge() {
        if ((uiState.value.minCount.toIntOrNull() ?: 0) == 0) {
            postSideEffect(GroupTypeEffect.ShowInputErrorSnackbar)
        } else {
            postSideEffect(GroupTypeEffect.CreateChallenge)
        }
    }

    fun moveNextStep() {
        intent {
            copy(groutTypeVisibility = true)
        }
    }

    fun moveToMatchingStep() {
        if ((uiState.value.minCount.toIntOrNull() ?: 0) == 0) {
            postSideEffect(GroupTypeEffect.ShowInputErrorSnackbar)
        } else {
            postSideEffect(GroupTypeEffect.MoveToMatchingStep)
        }
    }

    fun updateGroupType(groupType: GroupType) {
        intent {
            postSideEffect(GroupTypeEffect.UpdateGroupType(groupType))
            copy(groutType = groupType)
        }
    }

    fun updateMinCount(count: String) {
        intent {
            postSideEffect(GroupTypeEffect.UpdateMinCount(count))
            copy(minCount = count)
        }
    }
}
