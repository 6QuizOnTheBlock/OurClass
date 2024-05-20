package com.sixkids.core.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.content.Context
import android.os.Build
import android.os.ParcelUuid
import androidx.annotation.RequiresPermission
import java.nio.ByteBuffer
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BluetoothServer(context: Context) {
    private val bluetooth = context.getSystemService(Context.BLUETOOTH_SERVICE)
            as? BluetoothManager ?: throw Exception("This device doesn't support Bluetooth")

    private val bluetoothAdapter by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            bluetooth.adapter
        else {
            BluetoothAdapter.getDefaultAdapter()
        }
    }

    private var advertiseCallback: AdvertiseCallback? = null

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT])
    suspend fun startAdvertising(memberId: Long) {
        val advertiser: BluetoothLeAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser
            ?: throw Exception("This device doesn't support Bluetooth advertising")

        //if already advertising, ignore
        if (advertiseCallback != null) {
            return
        }

        val memberIdBytes = ByteBuffer.allocate(Long.SIZE_BYTES).putLong(memberId).array()
        val uuid = UUID.fromString(ULBAN_UUID)  // 고유 UUID


        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setConnectable(true)
            .setTimeout(0)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
            .build()

        val data = AdvertiseData.Builder()
            .setIncludeDeviceName(false)
            .setIncludeTxPowerLevel(false)
            .addServiceData(ParcelUuid(uuid), memberIdBytes)
            .build()

        advertiseCallback = suspendCoroutine { continuation ->
            val advertiseCallback = object : AdvertiseCallback() {
                override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
                    continuation.resume(this)
                }

                override fun onStartFailure(errorCode: Int) {
                    super.onStartFailure(errorCode)
                    throw Exception("Unable to start advertising, errorCode: $errorCode")
                }
            }
            advertiser.startAdvertising(settings, data, advertiseCallback)
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
    fun stopAdvertising() {
        val advertiser: BluetoothLeAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser
            ?: throw Exception("This device doesn't support Bluetooth advertising")

        advertiseCallback?.let {
            advertiser.stopAdvertising(it)
            advertiseCallback = null
        }
    }

    companion object {
        const val ULBAN_UUID = "0461c2e0-7d45-437f-929b-72aa4b355a96"
    }
}
