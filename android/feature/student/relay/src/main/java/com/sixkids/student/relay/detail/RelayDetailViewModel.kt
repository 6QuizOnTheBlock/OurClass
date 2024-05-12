package com.sixkids.student.relay.detail

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RelayDetailViewModel @Inject constructor(

) : BaseViewModel<RelayDetailState, RelayDetailSideEffect>(RelayDetailState())
{
    fun getRelayDetail() {

    }
}