package com.sixkids.student.relay.history

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.relay.GetRelayHistoryUseCase
import com.sixkids.domain.usecase.relay.GetRunningRelayUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.NotFoundException
import com.sixkids.model.Relay
import com.sixkids.model.RunningRelay
import com.sixkids.model.UserInfo
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class RelayHistoryViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getRunningRelayUseCase: GetRunningRelayUseCase,
    private val getRelayHistoryUseCase: GetRelayHistoryUseCase
) : BaseViewModel<RelayHistoryState, RelayHistoryEffect>(RelayHistoryState())
{
    private var orgId = 0L
    private lateinit var userInfo: UserInfo
    var relayHistory: Flow<PagingData<Relay>>? = null
    private var isFirstVisited: Boolean = true

    fun initData() = viewModelScope.launch {
        if (isFirstVisited.not()) return@launch
        isFirstVisited = false

        intent { copy(isLoading = true) }

        getSelectedOrganizationIdUseCase().onSuccess {
            orgId = it.toLong()
        }.onFailure {
            postSideEffect(RelayHistoryEffect.HandleException(it, ::initData))
        }

        loadUserInfoUseCase().onSuccess {
            userInfo = it
        }.onFailure {
            postSideEffect(RelayHistoryEffect.HandleException(it, ::initData))
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
            relayHistory = getRelayHistoryUseCase(organizationId = orgId.toInt(), memberId = userInfo.id)
                .cachedIn(viewModelScope)
        }
    }

    fun updateTotalCount(totalCount: Int) = intent { copy(totalRelayCount = totalCount) }

    fun navigateToRelayDetail(relayId: Long) = postSideEffect(
        RelayHistoryEffect.NavigateToRelayDetail(relayId)
    )

    fun navigateToAnswerRelay(relayId: Long) = postSideEffect(
        RelayHistoryEffect.NavigateToAnswerRelay(relayId)
    )

    fun navigateToTaggingReceiverRelay(relayId: Long) = postSideEffect(
        RelayHistoryEffect.NavigateToTaggingReceiverRelay(relayId)
    )

}