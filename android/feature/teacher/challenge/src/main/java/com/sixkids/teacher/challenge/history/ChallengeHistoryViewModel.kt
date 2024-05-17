package com.sixkids.teacher.challenge.history

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sixkids.domain.usecase.challenge.GetChallengeHistoryUseCase
import com.sixkids.domain.usecase.challenge.GetRunningChallengeUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.model.Challenge
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeHistoryViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getChallengeHistoryUseCase: GetChallengeHistoryUseCase,
    private val getRunningChallengeUseCase: GetRunningChallengeUseCase
) : BaseViewModel<ChallengeHistoryState, ChallengeHistoryEffect>(
    ChallengeHistoryState()
) {
    var challengeHistory: Flow<PagingData<Challenge>>? = null
    private var isFirstVisited: Boolean = true

    private var orgId = 0L

    fun initData() = viewModelScope.launch {
        if (isFirstVisited.not()) return@launch
        isFirstVisited = false

        getSelectedOrganizationIdUseCase().onSuccess {
            orgId = it.toLong()
        }.onFailure {
            postSideEffect(ChallengeHistoryEffect.HandleException(it, ::initData))
        }
        getChallengeHistory()
        getRunningChallenge()
    }

    fun navigateChallengeDetail(challengeId: Long) = postSideEffect(
        ChallengeHistoryEffect.NavigateToChallengeDetail(challengeId)
    )

    private fun getChallengeHistory() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            challengeHistory = getChallengeHistoryUseCase(orgId.toInt())
                .cachedIn(viewModelScope)
            intent { copy(isLoading = false) }
        }
    }

    private fun getRunningChallenge() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getRunningChallengeUseCase(1)
                .onSuccess {
                    intent { copy(isLoading = false, runningChallenge = it) }
                }.onFailure {
                    postSideEffect(
                        ChallengeHistoryEffect.HandleException(
                            it,
                            ::getRunningChallenge
                        )
                    )
                }
        }

    }

    fun updateTotalCount(totalCount: Int) {
        intent { copy(totalChallengeCount = totalCount) }
    }
}
