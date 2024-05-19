package com.sixkids.teacher.manageclass.statistics

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetOrganizationSummaryUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getStatisticsUseCase: GetOrganizationSummaryUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
) : BaseViewModel<StatisticsState, StatisticsEffect>(StatisticsState()){

    fun initData(){
        viewModelScope.launch {
            getSelectedOrganizationIdUseCase().onSuccess {
                getStatisticsUseCase(it).onSuccess {
                    Log.d(TAG, "initData: $it")
                    intent { copy(statistics = it) }
                }.onFailure {
                    Log.d(TAG, "initData2: $it")
                }
            }.onFailure {
                postSideEffect(StatisticsEffect.HandleException(it, ::initData))
            }
        }
    }
}