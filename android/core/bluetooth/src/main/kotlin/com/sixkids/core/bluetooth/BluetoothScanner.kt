package com.sixkids.core.bluetooth

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresPermission
import com.sixkids.core.bluetooth.BluetoothServer.Companion.ULBAN_UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.nio.ByteBuffer
import java.util.UUID

private const val TAG = "D107 ble"

class BluetoothScanner(context: Context) {
    private val bluetooth = context.getSystemService(Context.BLUETOOTH_SERVICE)
            as? BluetoothManager ?: throw Exception("This device doesn't support Bluetooth")

    private val scanner: BluetoothLeScanner
        get() = bluetooth.adapter.bluetoothLeScanner

    val isScanning = MutableStateFlow(false)
    val foundDevices = MutableStateFlow<List<Long>>(emptyList())

    private val scanCallback = object : ScanCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result ?: return

            val bytes = result.scanRecord?.getServiceData(ParcelUuid(UUID.fromString(ULBAN_UUID)))

            bytes?.let {
                val memberId = ByteBuffer.wrap(bytes).long
                if (!foundDevices.value.contains(memberId)) {
                    foundDevices.update { it + memberId }
                }
            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d(TAG, "onScanFailed: 실패")
            isScanning.value = false
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScanning() {
        scanner.startScan(scanCallback)
        isScanning.value = true
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScanning() {
        scanner.stopScan(scanCallback)
        isScanning.value = false
    }

    fun removeDevice(memberId: Long) {
        foundDevices.update { it - memberId }
    }

}
