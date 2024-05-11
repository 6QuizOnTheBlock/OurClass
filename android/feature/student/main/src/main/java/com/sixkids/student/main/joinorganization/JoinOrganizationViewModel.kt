package com.sixkids.student.main.joinorganization

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JoinOrganizationViewModel @Inject constructor(

): BaseViewModel<JoinOrganizationState, JoinOrganizationEffect>(JoinOrganizationState()){
}