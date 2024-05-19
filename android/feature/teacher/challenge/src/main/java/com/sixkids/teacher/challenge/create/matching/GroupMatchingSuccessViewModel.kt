package com.sixkids.teacher.challenge.create.matching

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.group.GetMatchingGroupUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupMatchingSuccessViewModel @Inject constructor(
    private val getMatchingGroupUseCase: GetMatchingGroupUseCase
) : BaseViewModel<GroupMatchingSuccessState, GroupMatchingSuccessEffect>(GroupMatchingSuccessState()) {

    fun updateMatchingSource(
        matchingSource: MatchingSource
    ) {
        intent {
            copy(
                orgId = matchingSource.orgId,
                minCount = matchingSource.minCount,
                matchingType = matchingSource.matchingType,
                members = matchingSource.members
            )
        }
    }

    fun getMatchingGroupList() {
        viewModelScope.launch {
            getMatchingGroupUseCase(
                uiState.value.orgId,
                uiState.value.minCount,
                uiState.value.matchingType.name,
                uiState.value.members
            ).onSuccess {
                intent {
                    copy(groupList = it)
                }
            }.onFailure {
                postSideEffect(GroupMatchingSuccessEffect.ShowSnackbar("그룹 만들기 실패"))
            }
        }
    }

}
