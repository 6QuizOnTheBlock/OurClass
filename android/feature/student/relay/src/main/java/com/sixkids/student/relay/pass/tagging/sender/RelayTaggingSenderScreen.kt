package com.sixkids.student.relay.pass.tagging.sender

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.core.nfc.HCEService
import com.sixkids.designsystem.component.screen.RelayTaggingScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "D107"

@Composable
fun RelayTaggingSenderRoute(
    viewModel: RelayTaggingSenderViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }
    RelayTaggingSenderScreen(uiState)

}

@Composable
fun RelayTaggingSenderScreen(
    uiState: RelayTaggingSenderState = RelayTaggingSenderState()
){
    if (uiState.relayNfc.relayId != -1L) {
        val serializedRelayNfc = Json.encodeToString(uiState.relayNfc)
        Log.d(TAG, "RelayTaggingSenderScreen: $serializedRelayNfc")
        HCEService.setData(serializedRelayNfc)
    }


    Box(modifier = Modifier.fillMaxSize()){
        RelayTaggingScreen(
            isSender = true
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RelayTaggingSenderScreenPreview() {
    RelayTaggingSenderScreen()
}