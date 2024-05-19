package com.sixkids.teacher.managestudent.main

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetOrganizationMemberUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageStudentMainViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getOrganizationMemberUseCase: GetOrganizationMemberUseCase,
    private val loadSelectedOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase
): BaseViewModel<ManageStudentMainState, ManageStudentMainEffect>(ManageStudentMainState()){
    fun initData(){
        viewModelScope.launch {
            loadSelectedOrganizationNameUseCase().onSuccess {
                intent { copy(classString = it) }
            }.onFailure {
                intent { copy(classString = "") }
            }

            getSelectedOrganizationIdUseCase().onSuccess {
                getOrganizationMemberUseCase(it)
                    .onSuccess {
                        intent { copy(studentList = it) }
                    }.onFailure {
                        postSideEffect(ManageStudentMainEffect.HandleException(it, ::initData))
                    }
            }.onFailure {
                postSideEffect(ManageStudentMainEffect.HandleException(it, ::initData))
            }
        }
    }
}