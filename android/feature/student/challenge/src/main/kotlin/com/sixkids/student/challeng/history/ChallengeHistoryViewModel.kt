package com.sixkids.student.challeng.history

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.challenge.GetChallengeHistoryUseCase
import com.sixkids.domain.usecase.challenge.GetRunningChallengeByStudentUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.model.UserInfo
import com.sixkids.ui.base.BaseViewModel
import com.sixkids.ui.extension.flatMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeHistoryViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getChallengeHistoryUseCase: GetChallengeHistoryUseCase,
    private val getRunningChallengeByStudentUseCase: GetRunningChallengeByStudentUseCase
) : BaseViewModel<ChallengeHistoryState, ChallengeHistoryEffect>(
    ChallengeHistoryState()
) {
    private var isFirstVisited: Boolean = true
    private var organizationId: Long = 0
    private lateinit var userInfo: UserInfo
    fun initData() = viewModelScope.launch {
        if (isFirstVisited.not()) return@launch
        isFirstVisited = false

        intent { copy(isLoading = true) }

        getUserInfoUseCase().flatMap { userInfo ->
            this@ChallengeHistoryViewModel.userInfo = userInfo
            getSelectedOrganizationIdUseCase().flatMap { organizationId ->
                this@ChallengeHistoryViewModel.organizationId = organizationId.toLong()
                val challengeHistory = getChallengeHistoryUseCase(organizationId, userInfo.id)
                intent { copy(challengeHistory = challengeHistory) }
                getRunningChallengeByStudentUseCase(organizationId)
            }.onSuccess {
                intent { copy(isLoading = false, runningChallenge = it) }
            }.onFailure {
            }
        }

        intent { copy(isLoading = false) }
    }

    fun navigateChallengeDetail(challengeId: Long) = postSideEffect(
        ChallengeHistoryEffect.NavigateToChallengeDetail(challengeId)
    )

    fun showGroupDialog() = postSideEffect(
        ChallengeHistoryEffect.ShowGroupDialog
    )

    fun navigateToCreateGroup() {
        val runningChallenge = uiState.value.runningChallenge ?: return
        postSideEffect(
            ChallengeHistoryEffect.NavigateToCreateGroup(
                runningChallenge.challenge.id,
                runningChallenge.type
            )
        )
    }

    private fun navigateToMatchedGroupCreate(){
        val runningChallenge = uiState.value.runningChallenge ?: return
        postSideEffect(
            ChallengeHistoryEffect.NavigateToMathedGroupCreate(
                runningChallenge.challenge.id,
                runningChallenge.memberNames
            )
        )
    }

    fun navigateToJoinGroup() = postSideEffect(
        ChallengeHistoryEffect.NavigateToJoinGroup(organizationId)
    )

    fun updateTotalCount(totalCount: Int) {
        intent { copy(totalChallengeCount = totalCount) }
    }

    fun navigateToCrateOrJoinGroup(leaderStatus: Boolean) {
        if (leaderStatus) {
            navigateToMatchedGroupCreate()
        } else {
            navigateToJoinGroup()
        }
    }
}
