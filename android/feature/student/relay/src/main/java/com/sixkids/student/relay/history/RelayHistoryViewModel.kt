package com.sixkids.student.relay.history

import androidx.lifecycle.viewModelScope
import com.sixkids.model.RunningRelay
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RelayHistoryViewModel @Inject constructor(
) : BaseViewModel<RelayHistoryState, RelayHistoryEffect>(RelayHistoryState())
{
    fun initData() = viewModelScope.launch {
        intent { copy(runningRelay = null) }
    }

    companion object{
        val runningRelayMyTurn = RunningRelay(
            id = 1,
            totalMemberCount = 20,
            doneMemberCount = 10,
            startTime = LocalDateTime.now().minusHours(1),
            endTime = LocalDateTime.now(),
            curMemberNickname = "홍유준",
            myTurnStatus = true,
            lastTurnMemberName = "오하빈"
        )

        val runningRelayNotMyTurn = RunningRelay(
            id = 1,
            totalMemberCount = 20,
            doneMemberCount = 10,
            startTime = LocalDateTime.now().minusHours(1),
            endTime = LocalDateTime.now(),
            curMemberNickname = "홍유준",
            myTurnStatus = false,
            lastTurnMemberName = "오하빈"
        )
    }
}