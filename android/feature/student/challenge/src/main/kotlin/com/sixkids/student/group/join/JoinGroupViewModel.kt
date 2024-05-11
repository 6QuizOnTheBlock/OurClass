package com.sixkids.student.group.join

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
) : BaseViewModel<JoinGroupState, JoinGroupEffect>(JoinGroupState()) {
}
