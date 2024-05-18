package com.sixkids.student.home.greeting.sender

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.core.nfc.HCEService
import com.sixkids.designsystem.component.screen.GreetingScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "D107"
@Composable
fun GreetingSenderRoute(
    viewModel: GreetingSenderViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    GreetingSenderScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )

}

@Composable
fun GreetingSenderScreen(
    uiState: GreetingSenderState = GreetingSenderState(),
    onBackClick: () -> Unit = {},
){
    if (uiState.greetingNfc.organizationId != -1) {
        val serializedGreetingNfc = Json.encodeToString(uiState.greetingNfc)
        Log.d(TAG, "RelayTaggingSenderScreen: $serializedGreetingNfc")
        HCEService.setData(serializedGreetingNfc)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GreetingScreen(
            isSender = true,
            onClick = onBackClick
        )
    }


}

@Composable
@Preview(showBackground = true)
fun GreetingSenderScreenPreview(){
    GreetingSenderScreen()
}