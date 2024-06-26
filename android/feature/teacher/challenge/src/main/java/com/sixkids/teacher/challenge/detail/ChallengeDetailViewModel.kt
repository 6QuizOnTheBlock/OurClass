package com.sixkids.teacher.challenge.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.challenge.GetChallengeDetailUseCase
import com.sixkids.domain.usecase.challenge.GradingReportUseCase
import com.sixkids.model.AcceptStatus
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.CHALLENGE_ID_NAME
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.GROUP_ID_NAME
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val getChallengeDetailUseCase: GetChallengeDetailUseCase,
    private val gradingReportUseCase: GradingReportUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChallengeDetailState, ChallengeDetailSideEffect>(
    ChallengeDetailState()
) {
    private val challengeId = savedStateHandle.get<Long>(CHALLENGE_ID_NAME)!!
    private val groupId = savedStateHandle.get<Long>(GROUP_ID_NAME)

    fun getChallengeDetail() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getChallengeDetailUseCase(challengeId, groupId).onSuccess {
                intent { copy(challengeDetail = it) }
            }.onFailure {
                postSideEffect(ChallengeDetailSideEffect.HandleException(it, ::getChallengeDetail))
            }
            intent { copy(isLoading = false) }
        }
    }

    fun gradingReport(reportId: Long, acceptStatus: AcceptStatus) {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            gradingReportUseCase(reportId, acceptStatus).onSuccess {
                intent { copy(isLoading = false, challengeDetail = challengeDetail.copy(
                    reportList = challengeDetail.reportList.map { report ->
                        if (report.id == reportId) {
                            report.copy(acceptStatus = acceptStatus)
                        } else {
                            report
                        }
                    }

                )) }
            }.onFailure {
                postSideEffect(ChallengeDetailSideEffect.HandleException(it) {
                    gradingReport(
                        reportId,
                        acceptStatus
                    )
                })
            }
        }
    }
}
