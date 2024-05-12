package com.sixkids.student.group.create

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothScanner
import com.sixkids.domain.usecase.user.GetMemberSimpleUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CreateGroupViewModel @Inject constructor(
    private val bluetoothScanner: BluetoothScanner,
    private val getMemberSimpleUseCase: GetMemberSimpleUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
) : BaseViewModel<CreateGroupState, CreateGroupEffect>(CreateGroupState()) {
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
