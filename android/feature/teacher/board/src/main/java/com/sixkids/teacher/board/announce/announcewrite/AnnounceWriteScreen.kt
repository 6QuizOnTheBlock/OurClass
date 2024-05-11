package com.sixkids.teacher.board.announce.announcewrite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.OrangeDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.board.R
import com.sixkids.teacher.board.post.postwrite.component.PageTitle

@Composable
fun AnnounceWriteRoute() {

}

@Composable
fun AnnounceWriteScreen(
    modifier: Modifier = Modifier,
    announceWriteState: AnnounceWriteState = AnnounceWriteState(),
    cancelOnClick: () -> Unit = {},
    submitOnClick: () -> Unit = {},
    titleValueChange: (String) -> Unit = {},
    contentValueChange: (String) -> Unit = {},
    addPhotoOnClick: () -> Unit = {}
) {

    val scrollState = rememberScrollState()

    LaunchedEffect(announceWriteState.content) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            PageTitle(
                title = stringResource(id = R.string.board_announce_write_title),
                cancelOnclick = cancelOnClick
            )
            //title
            OutlinedTextField(
                value = announceWriteState.title,
                onValueChange = titleValueChange,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.board_write_content_title),
                        style = UlbanTypography.bodyLarge
                    )
                },
                textStyle = UlbanTypography.bodyLarge
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.Black
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                //photo
                if (announceWriteState.photo != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        bitmap = announceWriteState.photo.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                //content
                OutlinedTextField(
                    value = announceWriteState.content,
                    onValueChange = { string ->
                        contentValueChange(string)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.board_write_content_content),
                            style = UlbanTypography.bodyLarge
                        )
                    },
                    textStyle = UlbanTypography.bodyLarge
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 이미지 추가 아이콘
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = addPhotoOnClick),
                    imageVector = ImageVector.vectorResource(id = com.sixkids.designsystem.R.drawable.ic_photo_camera),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(1f))
                // 등록 버튼
                UlbanFilledButton(
                    text = stringResource(id = R.string.board_write_submit),
                    onClick = submitOnClick,
                    color = Orange,
                    textColor = OrangeDark
                )
            }
        }

        if (announceWriteState.isLoading) {
            LoadingScreen()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AnnounceWriteScreenPreview() {
    AnnounceWriteScreen()
}
