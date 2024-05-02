package com.sixkids.teacher.challenge.create.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import java.time.LocalDateTime

@Composable
fun InfoContentRoute(
    viewModel: InfoViewModel = hiltViewModel(),
    updateTitle: (String) -> Unit,
    updateContent: (String) -> Unit,
    updateStartTime: (LocalDateTime) -> Unit,
    updateEndTime: (LocalDateTime) -> Unit,
    updatePoint: (Int) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is InfoEffect.UpdateTitle -> TODO()
            is InfoEffect.UpdateContent -> TODO()
            is InfoEffect.UpdateStartTime -> TODO()
            is InfoEffect.UpdateEndTime -> TODO()
            is InfoEffect.UpdatePoint -> TODO()
            is InfoEffect.ShowSnackbar -> {
                TODO()
            }
            InfoEffect.ShowKeyboard -> TODO()
        }
    }
    
    InfoContent(
        uiState = uiState,
    ) 
}

@Composable
fun InfoContent(
    uiState: InfoState = InfoState(),
    updateTitle: (String) -> Unit = {},
    updateContent: (String) -> Unit = {},
    updateStartTime: (LocalDateTime) -> Unit = {},
    updateEndTime: (LocalDateTime) -> Unit = {},
    updatePoint: (Int) -> Unit = {},
    onShowSnackbar: (SnackbarToken) -> Unit = {},
){
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "point")
        Text(text = "endTime")
        Text(text = "startTime")
        Text(text = "content")
        Text(text = "title")
    }
}


@Preview(showBackground = true)
@Composable
fun InfoContentPreview() {
    UlbanTheme {
        InfoContent()
    }
}
