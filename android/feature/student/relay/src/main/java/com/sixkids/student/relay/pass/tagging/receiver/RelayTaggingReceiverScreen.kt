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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.core.nfc.HCEService
import com.sixkids.designsystem.component.screen.RelayPassResultScreen
import com.sixkids.designsystem.component.screen.RelayTaggingScreen
import com.sixkids.designsystem.theme.Purple
import com.sixkids.model.RelayReceive
import com.sixkids.student.relay.pass.tagging.RelayNfc
import kotlinx.serialization.json.Json
import com.sixkids.designsystem.R as DesignSystemR

private const val TAG = "D107"

@Composable
fun RelayTaggingReceiverRoute(
    viewModel: RelayTaggingReceiverViewModel = hiltViewModel(),
    navigateToRelayHistory : () -> Unit
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

    if (uiState.relayReceive.senderName != ""){
        RelayTaggingResultScreen(uiState.relayReceive, navigateToRelayHistory)
    }else{
        RelayTaggingReceiverScreen()
    }

}

@Composable
fun RelayTaggingReceiverScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        RelayTaggingScreen(
            isSender = false
        )
    }
}

@Composable
fun RelayTaggingResultScreen(
    relayReceive: RelayReceive,
    navigateToRelayHistory: () -> Unit = {}
){
    Box(modifier = Modifier.fillMaxSize()){
        if (!relayReceive.lastStatus){
            RelayPassResultScreen(
                title = stringResource(id = DesignSystemR.string.relay_pass_result_title),
                subTitle = stringResource(DesignSystemR.string.relay_pass_result_subtitle_receiver),
                bodyTop = stringResource(id = DesignSystemR.string.relay_pass_result_body_top_receiver),
                bodyMiddle = "${relayReceive.senderName} 학생이",
                bodyBottom = stringResource(id = DesignSystemR.string.relay_pass_result_body_bottom_receiver),
                onClick = {navigateToRelayHistory()}
            )
        }else{
            RelayPassResultScreen(
                title = stringResource(id = DesignSystemR.string.relay_pass_result_title_bomb),
                subTitle = stringResource(DesignSystemR.string.relay_pass_result_subtitle_bomb, relayReceive.demerit),
                bodyTop = "${relayReceive.senderName} 학생이",
                bodyMiddle = "\'${relayReceive.question}\'",
                bodyBottom = stringResource(id = DesignSystemR.string.relay_pass_result_body_bottom_sender),
                imgRes = DesignSystemR.drawable.bomb,
                backgroundColor = Purple,
                onClick = {navigateToRelayHistory()}
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun RelayTaggingReceiverScreenPreview() {
    RelayTaggingReceiverScreen()
}

@Composable
@Preview(showBackground = true)
fun RelayTaggingResultScreenPreview() {
    RelayTaggingResultScreen(
        RelayReceive("오하빈", "", true, 0)
    )
}