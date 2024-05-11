package com.sixkids.student.group.create

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel

class CreateGroupViewModel @Inject constructor(): BaseViewModel<CreateGroupState, CreateGroupEffect>(CreateGroupState()){
}
