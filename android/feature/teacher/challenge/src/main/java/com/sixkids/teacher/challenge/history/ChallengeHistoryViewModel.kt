package com.sixkids.teacher.challenge.history

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sixkids.domain.usecase.GetChallengeHistoryUseCase
import com.sixkids.model.Challenge
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeHistoryViewModel @Inject constructor(
    private val getChallengeHistoryUseCase: GetChallengeHistoryUseCase
) : BaseViewModel<ChallengeHistoryState, ChallengeHistoryEffect>(
    ChallengeHistoryState()
) {
    var challengeHistory: Flow<PagingData<Challenge>>? = null

    fun navigateChallengeDetail(challengeId: Int) = postSideEffect(
        ChallengeHistoryEffect.NavigateToChallengeDetail(challengeId)
    )

    fun getChallengeHistory() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            challengeHistory = getChallengeHistoryUseCase(1)
                .cachedIn(viewModelScope)
            intent { copy(isLoading = false) }
        }
    }

}
