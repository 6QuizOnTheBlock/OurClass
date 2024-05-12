package com.sixkids.student.relay.create

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RelayCreateViewModel @Inject constructor(

): BaseViewModel<RelayCreateState, RelayCreateEffect>(RelayCreateState()){
}