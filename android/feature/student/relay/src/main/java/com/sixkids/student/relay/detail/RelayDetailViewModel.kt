package com.sixkids.student.relay.detail

import androidx.lifecycle.SavedStateHandle
import com.sixkids.student.relay.navigation.RelayRoute.RELAY_ID_NAME
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RelayDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RelayDetailState, RelayDetailSideEffect>(RelayDetailState())
{
    private val relayId = savedStateHandle.get<Long>(RELAY_ID_NAME)

    fun getRelayDetail() {

    }
}