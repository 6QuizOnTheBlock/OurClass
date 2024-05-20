package com.sixkids.teacher.manageclass.main

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageClassMainViewModel @Inject constructor(
    private val loadSelectedOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase
): BaseViewModel<ManageClassMainState, ManageClassMainEffect>(ManageClassMainState()){

    fun initData(){
        viewModelScope.launch {
            loadSelectedOrganizationNameUseCase().onSuccess {
                intent { copy(classString = it) }
            }.onFailure {
                intent { copy(classString = "")}
            }
        }
    }
}