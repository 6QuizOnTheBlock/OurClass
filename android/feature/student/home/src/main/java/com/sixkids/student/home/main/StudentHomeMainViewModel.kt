package com.sixkids.student.home.main

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentHomeMainViewModel @Inject constructor(

) : BaseViewModel<StudentHomeMainState, StudentHomeMainEffect>(StudentHomeMainState()){

}