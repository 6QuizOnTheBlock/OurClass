package com.sixkids.teacher.board.chatting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun ChattingRoute(
    viewModel: ChattingViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            else -> {}
        }
    }

    ChattingScreen()
}

@Composable
fun ChattingScreen(){

    Box(modifier = Modifier.fillMaxSize()){

    }
}

@Composable
@Preview(showBackground = true)
fun ChattingScreenPreview(){
    UlbanTheme {
        ChattingScreen()
    }
}