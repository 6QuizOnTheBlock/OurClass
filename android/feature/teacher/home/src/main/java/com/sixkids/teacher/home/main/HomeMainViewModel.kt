package com.sixkids.teacher.home.main

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val loadSelectedOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase
): BaseViewModel<HomeMainState, HomeMainEffect>(HomeMainState()){
    fun loadUserInfo(){
        viewModelScope.launch {
            loadUserInfoUseCase().onSuccess {
                intent {
                    copy(
                        teacherName = it.name,
                        teacherImageUrl = it.photo
                    )
                }
            }
        }
    }

    fun loadSelectedOrganizationName(){
        viewModelScope.launch {
            loadSelectedOrganizationNameUseCase().onSuccess {
                intent {
                    copy(
                        classString = it.replace("\n"," ")
                    )
                }
            }
        }
    }
}