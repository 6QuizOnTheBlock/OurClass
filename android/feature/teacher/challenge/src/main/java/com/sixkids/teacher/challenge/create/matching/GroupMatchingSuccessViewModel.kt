package com.sixkids.teacher.challenge.create.matching

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupMatchingSuccessViewModel @Inject constructor(

) : BaseViewModel<GroupMatchingSuccessState, GroupMatchingSuccessEffect>(GroupMatchingSuccessState()) {

}
