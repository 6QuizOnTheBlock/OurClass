package com.sixkids.teacher.board.chatting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.textfield.UlbanBasicTextField
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.board.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.designsystem.R as DesignSystemR

@Composable
fun ChattingRoute(
    viewModel: ChattingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            else -> {}
        }
    }

    ChattingScreen(
        uiState = uiState,
        onUpdateMessage = viewModel::updateMessage,
        onBackClick = onBackClick,
        onSendClick = viewModel::sendMessage,
        onPhotoClick = {
            //사진
        }
    )
}

@Composable
fun ChattingScreen(
    uiState: ChattingState = ChattingState(),
    onUpdateMessage: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onSendClick: (String) -> Unit = {},
    onPhotoClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            TopSection(uiState.organizationName, uiState.memberCount, onBackClick)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            )
            InputSection(
                msg = uiState.message,
                onUpdateMessage = onUpdateMessage,
                onSendClick = onSendClick,
                onPhotoClick = onPhotoClick
            )
        }
    }
}

@Composable
fun TopSection(
    organizationName: String = "",
    memberCount: Int = 0,
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = DesignSystemR.drawable.ic_arrow_back),
            contentDescription = "back button",
            modifier = Modifier.clickable { onBackClick() }
        )

        Text(
            text = "구미 초등학교 3학년 1반",
            style = UlbanTypography.titleSmall,
            modifier = Modifier.padding(10.dp, 0.dp)
        )

        Text(
            text = "21",
            style = UlbanTypography.titleSmall.copy(color = Gray),
        )
    }
}

@Composable
fun InputSection(
    msg: String = "",
    onUpdateMessage: (String) -> Unit = {},
    onSendClick: (String) -> Unit = {},
    onPhotoClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Cream)
            .padding(6.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = "photo",
            modifier = Modifier.size(30.dp)
        )

        UlbanBasicTextField(
            text = msg,
            onTextChange = onUpdateMessage,
            modifier = Modifier
                .padding(10.dp, 0.dp)
                .weight(1f)
                .wrapContentHeight(),
            maxLines = 3

        )

        Icon(
            Icons.AutoMirrored.Outlined.Send,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )

    }


}

@Composable
@Preview(showBackground = true)
fun ChattingScreenPreview() {
    UlbanTheme {
        ChattingScreen()
    }
}

@Composable
@Preview(showBackground = true)
fun TopSectionPreview() {
    UlbanTheme {
        TopSection()
    }
}

@Composable
@Preview(showBackground = true)
fun InputSectionPreview() {
    UlbanTheme {
        InputSection()
    }
}