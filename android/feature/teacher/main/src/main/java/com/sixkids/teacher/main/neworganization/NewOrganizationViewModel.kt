package com.sixkids.teacher.main.neworganization

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.NewOrganizationUseCase
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class NewOrganizationViewModel @Inject constructor(
    private val newOrganizationUseCase: NewOrganizationUseCase
) : BaseViewModel<NewOrganizationState, NewOrganizationEffect>(NewOrganizationState()) {

    fun updateSchoolName(name: String) {
        intent { copy(name = name) }
    }

    fun updateSchoolGrade(grade: String) {
        intent { copy(grade = grade) }
    }

    fun updateSchoolClass(classNo: String) {
        intent { copy(classNo = classNo) }
    }

    fun newClassClick() {
        val name = "${uiState.value.name}\n${uiState.value.grade}학년 ${uiState.value.classNo}반"
        Log.d(TAG, "newClassClick: $name")
        viewModelScope.launch {
            newOrganizationUseCase(name)
                .onSuccess {
                    postSideEffect(NewOrganizationEffect.NavigateToOrganizationList)
                }.onFailure {
                    Log.e(TAG, "newClassClick: ", it)
                    postSideEffect(
                        NewOrganizationEffect.OnShowSnackBar(
                            SnackbarToken(
                                it.message ?: "알 수 없는 오류가 발생했습니다."
                            )
                        )
                    )
                }
        }
    }

}