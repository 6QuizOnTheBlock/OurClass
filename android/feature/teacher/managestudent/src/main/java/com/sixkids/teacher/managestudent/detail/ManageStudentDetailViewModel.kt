package com.sixkids.teacher.managestudent.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.organization.GetStudentDetailUseCase
import com.sixkids.teacher.managestudent.navigation.ManageStudentRoute.STUDENT_ID_NAME
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class ManageStudentDetailViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getStudentDetailUseCase: GetStudentDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ManageStudentDetailState, ManageStudentDetailEffect>(ManageStudentDetailState()){
    private val studentId = savedStateHandle.get<Long>(STUDENT_ID_NAME)

    fun initData(){
        viewModelScope.launch {
            getSelectedOrganizationIdUseCase().onSuccess {
                getStudentDetailUseCase(it.toLong(), studentId!!).onSuccess {member ->
                    intent { copy(memberDetail = member) }
                }.onFailure {
                    postSideEffect(
                        ManageStudentDetailEffect.HandleException(it, ::initData)
                    )
                }
            }.onFailure {
                postSideEffect(
                    ManageStudentDetailEffect.HandleException(it, ::initData)
                )
            }
        }
    }


}