package com.sixkids.teacher.board.postwrite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.board.R
import com.sixkids.teacher.board.postwrite.component.PageTitle
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun PostWriteRoute(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(padding)
    ) {
        PostWriteScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostWriteScreen(
    modifier: Modifier = Modifier,
    cancelOnClick: () -> Unit = {},
    submitOnClick: (title: String, content: String, anonymousChecked: Boolean) -> Unit = { _, _, _ -> } // TODO : 사진 파일 추가
) {
    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    var anonymousChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        PageTitle(
            title = stringResource(id = R.string.board_write_title)
        )
        //title
        OutlinedTextField(
            value = titleText,
            onValueChange = { string: String ->
                titleText = string
            },
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
        //content
        OutlinedTextField(
            value = contentText,
            onValueChange = { string: String ->
                contentText = string
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
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이미지 추가 아이콘
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_photo_camera),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            // 익명 체크박스
            Checkbox(
                modifier = Modifier.scale(1.2f),
                checked = anonymousChecked,
                onCheckedChange = {
                    anonymousChecked = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Blue,
                    uncheckedColor = Color.Black
                )
            )
            Text(
                text = stringResource(id = R.string.board_write_anonymous),
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            // 등록 버튼
            UlbanFilledButton(
                text = stringResource(id = R.string.board_write_submit),
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostWriteScreenPreview() {
    PostWriteScreen()
}