package com.sixkids.teacher.home.rank.component

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetClassRankUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.teacher.home.rank.RankEffect
import com.sixkids.teacher.home.rank.RankState
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val loadSelectedOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase,
    private val getClassRankUseCase: GetClassRankUseCase
): BaseViewModel<RankState, RankEffect>(RankState()){
    private var organizationId: Int? = null

    private suspend fun getOrganizationId() {
        organizationId = getSelectedOrganizationIdUseCase().getOrNull()
    }

    fun getOrganizationName() {
        viewModelScope.launch {
            loadSelectedOrganizationNameUseCase().onSuccess {
                intent { copy(classString = it) }
            }
        }
    }

    fun getClassRank() {
        viewModelScope.launch {
            if (organizationId == null) {
                getOrganizationId()
            }

            if (organizationId != null) {
                val result = getClassRankUseCase(organizationId!!)
                result.onSuccess {
                    intent { copy(rankList = it) }
                }
                result.onFailure {
                    postSideEffect(RankEffect.onShowSnackBar(it.message ?: "학급 랭킹 불러오기에 실패했습니다 ;("))
                }
            } else {
                postSideEffect(RankEffect.onShowSnackBar("학급 정보를 불러오는데 실패했습니다 ;("))
            }
        }
    }
}