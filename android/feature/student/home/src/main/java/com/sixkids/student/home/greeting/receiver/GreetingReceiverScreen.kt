package com.sixkids.student.home.greeting.receiver

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.core.nfc.HCEService
import com.sixkids.designsystem.component.screen.GreetingScreen
import com.sixkids.designsystem.component.screen.RelayTaggingScreen
import com.sixkids.model.GreetingNFC
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import kotlinx.serialization.json.Json

private const val TAG = "D107"
@Composable
fun GreetingReceiverRoute(
    viewModel: GreetingReceiverViewModel = hiltViewModel(),
    onBackClick : () -> Unit = {},
    onShowSnackBar : (SnackbarToken) -> Unit = {}
){
    val context = LocalContext.current
    val activity = context as Activity

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val nfcAdapter: NfcAdapter = NfcAdapter.getDefaultAdapter(context)

    DisposableEffect(key1 = Unit) {
        viewModel.initData()

        onDispose {
            nfcAdapter.disableReaderMode(activity)
        }
    }

    viewModel.sideEffect.collectWithLifecycle {
        when(it){
            is GreetingReceiverEffect.OnShowSnackBar -> onShowSnackBar(it.tkn)
            is GreetingReceiverEffect.NavigateToHome -> onBackClick()
        }
    }

    if (uiState.organizationId != -1 && nfcAdapter.isEnabled) {
        Log.d(TAG, "RelayTaggingReceiverRoute: Ready!")
        nfcAdapter.enableReaderMode(activity, { tag : Tag? ->
            tag?.let {
                val isoDep = IsoDep.get(it)
                isoDep.use { iso ->
                    iso.connect()
                    val response = isoDep.transceive(HCEService.SELECT_APDU)
                    val message = String(response.copyOfRange(0, response.size - 2))
                    Log.d(TAG, "GreetingReceiverRoute: $message")
                    val greetingNfc = Json.decodeFromString<GreetingNFC>(message)
                    viewModel.onNfcReceived(greetingNfc)
                }
            }
        }, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null)

    }

    GreetingReceiverScreen()
}

@Composable
fun GreetingReceiverScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        GreetingScreen(
            isSender = false
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GreetingReceiverScreenPreview(){
    GreetingReceiverScreen()
}