package com.sixkids.student.group.create

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothScanner
import com.sixkids.domain.usecase.user.GetMemberSimpleUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CreateGroupViewModel @Inject constructor(
    private val bluetoothScanner: BluetoothScanner,
    private val getMemberSimpleUseCase: GetMemberSimpleUseCase,
) : BaseViewModel<CreateGroupState, CreateGroupEffect>(CreateGroupState()) {

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan() {
        bluetoothScanner.startScanning()
        viewModelScope.launch {
            bluetoothScanner.foundDevices.collect { devices ->
                if (devices.isEmpty()) return@collect
//                val id = devices.last().split("-").last()
                val id = 9L
                if (uiState.value.selectedMembers.none { it.id == id }) {
                    getMemberSimpleUseCase(id).onSuccess { member ->
                        intent {
                            copy(
                                foundMembers = foundMembers.toMutableList().apply {
                                    add(member)
                                }
                            )
                        }
                    }.onFailure {  }
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
            bluetoothScanner.removeDevice("sixkids-${memberId}")
            copy(
                selectedMembers = selectedMembers.toMutableList().apply {
                    removeIf { it.id == memberId }
                }
            )
        }
    }
}
