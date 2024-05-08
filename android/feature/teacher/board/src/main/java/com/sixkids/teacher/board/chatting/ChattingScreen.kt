package com.sixkids.teacher.board.chatting

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sixkids.designsystem.component.textfield.UlbanBasicTextField
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.GrayLight
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.model.Chat
import com.sixkids.teacher.board.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import java.text.SimpleDateFormat
import java.util.TimeZone
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

    LaunchedEffect(key1 = Unit) {
        viewModel.initChatData()
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
            TopSection(
                uiState.organizationName,
                uiState.memberCount, onBackClick
            )

            ChatSection(
                uiState.chatList,
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
fun ChatSection(chatList: List<Chat>, modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()

    Column(modifier = modifier) {
        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {
            items(chatList) { chat ->
                if (chat.memberId == 1L) {
                    MyChat(chat)
                } else {
                    OtherChat(chat)
                }
            }
        }
    }

    if (chatList.isNotEmpty()) {
        LaunchedEffect(chatList.size) {
            scrollState.scrollToItem(chatList.size - 1)
        }
    }

}

@Composable
fun OtherChat(chat: Chat) {
    val screenWidthDp = with(LocalDensity.current) {
        LocalContext.current.resources.displayMetrics.widthPixels.toDp()
    }
    val maxWidthDp = screenWidthDp * 0.6f
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = chat.memberProfilePhoto,
                contentDescription = "profile photo",
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = chat.memberName,
                style = UlbanTypography.bodySmall,
                modifier = Modifier.padding(10.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp, 0.dp, 0.dp, 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {

            Text(
                text = chat.content,
                style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier
                    .background(GrayLight, shape = RoundedCornerShape(10.dp))
                    .padding(12.dp)
                    .widthIn(max = maxWidthDp)
                    .wrapContentWidth()
            )
            Text(
                text = chatTimeFormat(chat.sendDateTime),
                style = UlbanTypography.bodySmall.copy(
                    fontSize = 8.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 6.dp)
            )
        }
    }
}

@Composable
fun MyChat(chat: Chat) {
    val screenWidthDp = with(LocalDensity.current) {
        LocalContext.current.resources.displayMetrics.widthPixels.toDp()
    }
    val maxWidthDp = screenWidthDp * 0.6f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = chatTimeFormat(chat.sendDateTime),
            style = UlbanTypography.bodySmall.copy(
                fontSize = 8.sp,
                lineHeight = 10.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 6.dp)
        )
        Text(
            text = chat.content,
            style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            modifier = Modifier
                .background(Yellow, shape = RoundedCornerShape(10.dp))
                .padding(12.dp)
                .widthIn(max = maxWidthDp)
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun chatTimeFormat(time: Long): String {
    val formatter = SimpleDateFormat("MM/dd\na h:mm").apply {
        timeZone = TimeZone.getTimeZone("Asia/Seoul")
    }

    return formatter.format(time)
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
            maxLines = 3,
            textStyle = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Normal)

        )

        Icon(
            Icons.AutoMirrored.Outlined.Send,
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    onSendClick(msg)
                }
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


/*
@Composable
@Preview(showBackground = true)
fun MyChatPreview() {
    UlbanTheme {
        MyChat()
    }
}

@Composable
@Preview(showBackground = true)
fun OtherChatPreview() {
    UlbanTheme {
        OtherChat()
    }
}

 */