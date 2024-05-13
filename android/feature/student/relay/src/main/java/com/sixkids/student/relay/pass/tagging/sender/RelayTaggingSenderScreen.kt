package com.sixkids.student.relay.pass.tagging.sender

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.screen.RelayTaggingScreen

@Composable
fun RelayTaggingSenderRoute(
    viewModel: RelayTaggingSenderViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    RelayTaggingScreen()

}

@Composable
fun RelayTaggingSenderScreen(){
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