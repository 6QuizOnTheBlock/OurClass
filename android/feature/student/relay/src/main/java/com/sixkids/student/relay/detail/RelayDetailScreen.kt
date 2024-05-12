package com.sixkids.student.relay.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.ui.extension.collectWithLifecycle


@Composable
fun RelayDetailRoute(
    viewModel: RelayDetailViewModel = hiltViewModel(),
    handleException: (Throwable, () -> Unit) -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is RelayDetailSideEffect.HandleException -> handleException(it.throwable, it.retry)
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getRelayDetail()
    }

    RelayDetailScreen(
        uiState = uiState
    )

}

@Composable
fun RelayDetailScreen(
    uiState: RelayDetailState = RelayDetailState()
) {
}

@Composable
@Preview(showBackground = true)
fun RelayDetailScreenPreview() {
    UlbanTheme {
        RelayDetailScreen()
    }
}
