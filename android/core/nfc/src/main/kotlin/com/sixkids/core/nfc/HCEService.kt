package com.sixkids.core.nfc

import android.nfc.cardemulation.HostApduService
import android.os.Bundle

class HCEService : HostApduService() {
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        if (commandApdu == null) {
            return FAIL_SW
        }

        // Check if APDU matches the SELECT APDU defined above
        return if (commandApdu contentEquals SELECT_APDU) {
            // Data to be sent to B screen
            "Hello from A".toByteArray() + SUCCESS_SW
        } else {
            FAIL_SW
        }
    }

    override fun onDeactivated(p0: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        val SELECT_APDU = byteArrayOf(0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte(), 0x07.toByte(), 0xF0.toByte(), 0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(), 0x06.toByte())
        val SUCCESS_SW = byteArrayOf(0x90.toByte(), 0x00.toByte()) // Status word (SW) for success
        val FAIL_SW = byteArrayOf(0x6F.toByte(), 0x00.toByte())
    }
}
