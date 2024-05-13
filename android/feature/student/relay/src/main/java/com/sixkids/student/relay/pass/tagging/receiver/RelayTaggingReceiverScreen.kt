package com.sixkids.student.relay.pass.tagging.receiver

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sixkids.designsystem.component.screen.RelayTaggingScreen


@Composable
fun RelayTaggingReceiverRoute(
    viewModel: RelayTaggingReceiverViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    RelayTaggingScreen()

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
@Preview(showBackground = true)
fun RelayTaggingReceiverScreenPreview() {
    RelayTaggingReceiverScreen()
}