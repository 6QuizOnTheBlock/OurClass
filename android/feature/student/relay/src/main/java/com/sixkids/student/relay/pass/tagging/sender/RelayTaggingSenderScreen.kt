package com.sixkids.student.relay.pass.tagging.sender

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.core.nfc.HCEService
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.screen.RelayPassResultScreen
import com.sixkids.designsystem.component.screen.RelayTaggingScreen
import com.sixkids.model.RelaySend
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "D107"

@Composable
fun RelayTaggingSenderRoute(
    viewModel: RelayTaggingSenderViewModel = hiltViewModel(),
    navigateToRelayHistory: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is RelayTaggingSenderEffect.NavigateToTaggingResult -> navigateToRelayHistory()
            is RelayTaggingSenderEffect.OnShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    if (uiState.relaySend.prevMemberName == "" && uiState.relaySend.prevQuestion == "") {
        RelayTaggingSenderScreen(
            uiState,
            checkRelaySent = viewModel::checkRelaySent
        )
    } else {
        RelayTaggingResultScreen(
            relaySend = uiState.relaySend,
            navigateToRelayHistory = navigateToRelayHistory
        )
    }


}

@Composable
fun RelayTaggingSenderScreen(
    uiState: RelayTaggingSenderState = RelayTaggingSenderState(),
    checkRelaySent: () -> Unit = {}
) {
    if (uiState.relayNfc.relayId != -1L) {
        val serializedRelayNfc = Json.encodeToString(uiState.relayNfc)
        Log.d(TAG, "RelayTaggingSenderScreen: $serializedRelayNfc")
        HCEService.setData(serializedRelayNfc)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        RelayTaggingScreen(
            isSender = true,
            onClick = checkRelaySent
        )
    }
}

@Composable
fun RelayTaggingResultScreen(
    relaySend: RelaySend,
    navigateToRelayHistory: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        RelayPassResultScreen(
            title = stringResource(id = R.string.relay_pass_result_title),
            subTitle = stringResource(R.string.relay_pass_result_subtitle_sender),
            bodyTop = if (relaySend.prevMemberName != "") "${relaySend.prevMemberName} 학생이"
                else relaySend.prevMemberName,
            bodyMiddle = "\'${relaySend.prevQuestion}\'",
            bodyBottom = if (relaySend.prevMemberName != "") stringResource(id = R.string.relay_pass_result_body_bottom_sender)
                else "",
            onClick = { navigateToRelayHistory() }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RelayTaggingSenderScreenPreview() {
    RelayTaggingResultScreen(
        relaySend = RelaySend(
            prevMemberName = "김철수",
            prevQuestion = "오늘 점심은 뭐먹지?"
        )
    )
}