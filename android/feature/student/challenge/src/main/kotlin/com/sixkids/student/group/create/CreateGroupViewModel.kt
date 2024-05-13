package com.sixkids.student.group.create

import android.Manifest
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothScanner
import com.sixkids.domain.usecase.group.CreateGroupMatchingRoomUseCase
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.domain.usecase.user.GetMemberSimpleUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.student.challenge.BuildConfig
import com.sixkids.ui.base.BaseViewModel
import com.sixkids.ui.extension.flatMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val bluetoothScanner: BluetoothScanner,
    private val getMemberSimpleUseCase: GetMemberSimpleUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getATKUseCase: GetATKUseCase,
    private val createGroupMatchingRoomUseCase: CreateGroupMatchingRoomUseCase
) : BaseViewModel<CreateGroupState, CreateGroupEffect>(CreateGroupState()) {

    private var eventSource: EventSource? = null

    private val client = OkHttpClient.Builder()
        .addInterceptor {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            }.intercept(it)
        }
        .connectTimeout(10, TimeUnit.MINUTES)
        .readTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .build()

    private val request = Request.Builder()
        .url(BuildConfig.SSE_ENDPOINT)
        .header("Authorization", "Bearer ${runBlocking { getATKUseCase().getOrNull() }}")
        .build()

    private val eventSourceListener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            Log.d(TAG, "Connection Opened")
        }

        override fun onClosed(eventSource: EventSource) {
            super.onClosed(eventSource)
            Log.d(TAG, "Connection Closed")
        }

        override fun onEvent(
            eventSource: EventSource,
            id: String?,
            type: String?,
            data: String
        ) {
            super.onEvent(eventSource, id, type, data)
            Log.d(TAG, "On Event Received! Data -: $data")
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            Log.d(TAG, "On Failure -: ${response?.body}")
        }
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            loadUserInfoUseCase().onSuccess { member ->
                intent {
                    copy(
                        leader = MemberSimple(
                            id = member.id.toLong(),
                            name = member.name,
                            photo = member.photo
                        )
                    )
                }
            }.onFailure {
                postSideEffect(CreateGroupEffect.HandleException(it, ::loadUserInfo))
            }
        }
    }

    fun createGroupMatchingRoom(challengeId: Long) {
        viewModelScope.launch {
            createGroupMatchingRoomUseCase(challengeId).flatMap { matchingRoom ->
                intent {
                    copy(
                        roomKey = matchingRoom.dataKey,
                        groupSize = matchingRoom.minCount,
                    )
                }
                loadUserInfoUseCase()
            }.onSuccess { member ->
                intent {
                    copy(
                        leader = MemberSimple(
                            id = member.id.toLong(),
                            name = member.name,
                            photo = member.photo
                        )
                    )
                }
            }.onFailure {
                postSideEffect(CreateGroupEffect.HandleException(it) {
                    createGroupMatchingRoom(challengeId)
                })
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan() {
        bluetoothScanner.startScanning()
        viewModelScope.launch {
            bluetoothScanner.foundDevices.collect { memberIds ->
                if (memberIds.isEmpty()) return@collect
                val newMembers = mutableListOf<MemberSimple>()
                for (memberId in memberIds) {
                    getMemberSimpleUseCase(memberId).onSuccess { member ->
                        newMembers.add(member)
                    }.onFailure {
                        stopScan()
                        postSideEffect(CreateGroupEffect.HandleException(it) {
                            startScan()
                        })
                    }
                }
                intent {
                    copy(foundMembers = newMembers)
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScan() {
        bluetoothScanner.stopScanning()
    }

    fun connectSse() = viewModelScope.launch {
        eventSource = EventSources.createFactory(client)
            .newEventSource(request, eventSourceListener)

    }

    fun disconnectSse() {
        eventSource?.cancel()
        eventSource = null
    }

    fun selectMember(member: MemberSimple) {
        intent {
            copy(
                foundMembers = foundMembers.toMutableList().apply {
                    remove(member)
                },
                selectedMembers = selectedMembers.toMutableList().apply {
                    add(member)
                }
            )
        }
    }

    fun removeMember(memberId: Long) {
        intent {
            bluetoothScanner.removeDevice(memberId)
            copy(
                selectedMembers = selectedMembers.toMutableList().apply {
                    removeIf { it.id == memberId }
                }
            )
        }
    }
}
