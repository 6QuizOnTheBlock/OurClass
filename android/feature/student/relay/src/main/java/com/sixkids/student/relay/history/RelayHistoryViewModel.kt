package com.sixkids.student.relay.history

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.relay.GetRunningRelayUseCase
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.model.NotFoundException
import com.sixkids.model.RunningRelay
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RelayHistoryViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getRunningRelayUseCase: GetRunningRelayUseCase,
) : BaseViewModel<RelayHistoryState, RelayHistoryEffect>(RelayHistoryState())
{
    var orgId = 0L

    fun initData() = viewModelScope.launch {
        intent { copy(isLoading = true) }

        getSelectedOrganizationIdUseCase().onSuccess {
            orgId = it.toLong()
        }.onFailure {
            //todo
        }

        getRunningRelay()
        getRelayHistory()

        intent { copy(isLoading = false) }
    }

    private fun getRunningRelay() {
        viewModelScope.launch {
            getRunningRelayUseCase(organizationId = orgId)
                .onSuccess {
                    intent { copy(runningRelay = it) }
                }.onFailure {
                    if (it is NotFoundException){
                        intent { copy(runningRelay = null) }
                    }else{
                        postSideEffect(
                            RelayHistoryEffect.HandleException(
                                it,
                                ::getRunningRelay
                            )
                        )
                    }
                }
        }
    }

    private fun getRelayHistory() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            //todo
            intent { copy(isLoading = false) }
        }
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
        )

        val runningRelayNotMyTurn = RunningRelay(
            id = 1,
            totalMemberCount = 20,
            doneMemberCount = 10,
            startTime = LocalDateTime.now().minusHours(1),
            endTime = LocalDateTime.now(),
            curMemberNickname = "홍유준",
            myTurnStatus = false,
        )
    }
}