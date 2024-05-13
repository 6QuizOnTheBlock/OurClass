package com.sixkids.student.relay.pass.tagging.receiver

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.core.nfc.HCEService
import com.sixkids.designsystem.component.screen.RelayTaggingScreen
import com.sixkids.student.relay.pass.tagging.RelayNfc
import kotlinx.serialization.json.Json

private const val TAG = "D107"

@Composable
fun RelayTaggingReceiverRoute(
    viewModel: RelayTaggingReceiverViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val activity = context as Activity

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val bleAdapter: NfcAdapter = NfcAdapter.getDefaultAdapter(context)

    DisposableEffect(key1 = Unit) {
        viewModel.init()

        onDispose {
            bleAdapter.disableReaderMode(activity)
        }
    }

    if (uiState.relayId != -1L && bleAdapter.isEnabled) {
        Log.d(TAG, "RelayTaggingReceiverRoute: Ready!")
        bleAdapter.enableReaderMode(activity, { tag : Tag? ->
            tag?.let {
                val isoDep = IsoDep.get(it)
                isoDep.use { iso ->
                    iso.connect()
                    val response = isoDep.transceive(HCEService.SELECT_APDU)
                    val message = String(response.copyOfRange(0, response.size - 2))
                    Log.d(TAG, "RelayTaggingReceiverRoute: $message")
                    val relayNfc = Json.decodeFromString<RelayNfc>(message)
                    viewModel.onNfcReceived(relayNfc)
                }
            }
        }, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null)

    }

    RelayTaggingReceiverScreen(uiState)

}

@Composable
fun RelayTaggingReceiverScreen(
    uiState: RelayTaggingReceiverState = RelayTaggingReceiverState()
){
    if (uiState.relayId != -1L) {

    }
    Box(modifier = Modifier.fillMaxSize()){
        RelayTaggingScreen(
            isSender = false
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RelayTaggingReceiverScreenPreview() {
    RelayTaggingReceiverScreen()
}