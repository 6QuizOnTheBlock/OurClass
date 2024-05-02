package com.sixkids.teacher.challenge.create.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.textfield.InputTextType
import com.sixkids.designsystem.component.textfield.UlbanUnderLineWithTitleTextField
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R
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
    updatePoint: (String) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is InfoEffect.UpdateTitle -> updateTitle(it.title)
            is InfoEffect.UpdateContent -> updateContent(it.content)
            is InfoEffect.UpdateStartTime -> updateStartTime(it.startTime)
            is InfoEffect.UpdateEndTime -> updateEndTime(it.endTime)
            is InfoEffect.UpdatePoint -> updatePoint(it.point)
            is InfoEffect.ShowSnackbar -> onShowSnackbar(it.token)
        }
    }

    InfoContent(
        uiState = uiState,
        updateTitle = viewModel::updateTitle,
        updateContent = viewModel::updateContent,
        updatePoint = viewModel::updatePoint,
        updateStartTime = viewModel::updateStartTime,
        updateEndTime = viewModel::updateEndTime,
    )
}

@Composable
fun InfoContent(
    uiState: InfoState = InfoState(),
    updateTitle: (String) -> Unit = {},
    updateContent: (String) -> Unit = {},
    updateStartTime: (LocalDateTime) -> Unit = {},
    updateEndTime: (LocalDateTime) -> Unit = {},
    updatePoint: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
    ) {
        val titleStyle = UlbanTypography.titleSmall
        Text(text = stringResource(R.string.please_input_point), style = titleStyle)
        UlbanUnderLineWithTitleTextField(
            text = uiState.point,
            onTextChange = { updatePoint(it) },
            hint = stringResource(R.string.point_hint),
            onIconClick = { updatePoint("") },
            inputTextType = InputTextType.POINT,
        )
        Text(text = "endTime")
        Text(text = "startTime")
        Text(text = stringResource(R.string.please_input_content), style = titleStyle)
        UlbanUnderLineWithTitleTextField(
            text = uiState.content,
            onTextChange = { updateContent(it) },
            onIconClick = { updateContent("") },
            inputTextType = InputTextType.TEXT,
        )
        Text(text = stringResource(R.string.please_input_title), style = titleStyle)
        UlbanUnderLineWithTitleTextField(
            text = uiState.title,
            onTextChange = { updateTitle(it) },
            onIconClick = { updateTitle("") },
            inputTextType = InputTextType.TEXT,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun InfoContentPreview() {
    UlbanTheme {
        InfoContent()
    }
}
