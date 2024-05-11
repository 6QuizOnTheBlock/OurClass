package com.sixkids.student.group.create

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothScanner
import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CreateGroupViewModel @Inject constructor(
    private val bluetoothScanner: BluetoothScanner,
) : BaseViewModel<CreateGroupState, CreateGroupEffect>(CreateGroupState()) {

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan() {
        bluetoothScanner.startScanning()
        viewModelScope.launch {
            bluetoothScanner.foundDevices.collect { devices ->
                if (devices.isEmpty()) return@collect
                val (name, id) = devices.first().split("-")
                if (uiState.value.selectedMembers.none { it.id == id.toLong() }) {
                    intent {
                        copy(
                            foundMembers = foundMembers.toMutableList().apply {
                                add(
                                    //TODO: 사용자 정보 받아오는 API 구현 필
                                    MemberSimple(
                                        id = id.toLong(),
                                        name = name,
                                        photo = "https://static01.nyt.com/images/2021/09/14/science/07CAT-STRIPES/07CAT-STRIPES-jumbo.jpg?quality=75&auto=webp"
                                    )
                                )
                            }
                        )
                    }
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
