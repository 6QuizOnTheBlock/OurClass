package com.sixkids.designsystem.component.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun UlbanUnderLineTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    hint: String = "",
    onIconClick: () -> Unit = {},
    inputTextType: InputTextType = InputTextType.TEXT,
    textStyle: TextStyle = UlbanTypography.bodyMedium,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (inputTextType) {
                InputTextType.TEXT -> {
                    UlbanBasicTextField(
                        modifier = modifier
                            .weight(1f),
                        text = text,
                        onTextChange = onTextChange,
                        hint = hint,
                        textStyle = textStyle,
                        keyboardActions = keyboardActions,
                        keyboardOptions = keyboardOptions,
                        visualTransformation = visualTransformation
                    )
                }

                InputTextType.POINT -> {
                    UlbanNumberTextField(
                        modifier = Modifier
                            .weight(1f),
                        text = text,
                        onTextChange = onTextChange,
                        hint = hint,
                        textStyle = textStyle,
                        keyboardActions = keyboardActions,
                        postfix = stringResource(id = R.string.point_postfix)
                    )
                }

                InputTextType.PEOPLE -> {
                    UlbanNumberTextField(
                        modifier = Modifier
                            .weight(1f),
                        text = text,
                        onTextChange = onTextChange,
                        hint = hint,
                        textStyle = textStyle,
                        keyboardActions = keyboardActions,
                        postfix = stringResource(id = R.string.people_postfix)
                    )
                }
            }
            if (text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onIconClick() }
                        .padding(horizontal = 8.dp)
                        .size(24.dp)
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Blue,
            thickness = 2.dp
        )
    }
}


enum class InputTextType {
    TEXT,
    POINT,
    PEOPLE,
}

@Preview(showBackground = true)
@Composable
fun UlbanUnderLineTextFieldPreview() {
    var text by remember { mutableStateOf("4월 22일 함께 달리기") }
    var point by remember { mutableStateOf("1200") }
    var peopleCnt by remember { mutableStateOf("") }
    Column(
//        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = "제목을 입력해 주세요", style = UlbanTypography.titleSmall)
        UlbanUnderLineTextField(
            text = text,
            onTextChange = { text = it },
            hint = "hint",
            onIconClick = {
                text = ""
            },
            inputTextType = InputTextType.TEXT
        )

        Text(text = "포인트를 입력해 주세요", style = UlbanTypography.titleSmall)
        UlbanUnderLineTextField(
            text = point,
            onTextChange = { point = it },
            hint = "hint",
            onIconClick = {
                point = ""
            },
            inputTextType = InputTextType.POINT
        )

        Text(text = "그룹 최소 인원을 설정해 주세요", style = UlbanTypography.titleSmall)
        UlbanUnderLineTextField(
            text = peopleCnt,
            onTextChange = { peopleCnt = it },
            hint = "hint",
            onIconClick = {
                peopleCnt = ""
            },
            inputTextType = InputTextType.PEOPLE
        )
    }
}
