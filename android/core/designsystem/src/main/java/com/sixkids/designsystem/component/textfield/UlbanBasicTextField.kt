package com.sixkids.designsystem.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography


@Composable
fun UlbanBasicTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    maxLines: Int = 1,
    minLines: Int = 1,
    onTextChange: (String) -> Unit = {},
    hint: String = "",
    textStyle: TextStyle = UlbanTypography.bodyLarge,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        textStyle = textStyle,
        maxLines = maxLines,
        minLines = minLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    style = textStyle.copy(color = Gray),
                )
            }
            innerTextField()
        }
    }
}


@Preview
@Composable
fun UlbanBasicTextFieldPreview() {
    UlbanTheme {
        var text by remember { mutableStateOf("") }
        UlbanBasicTextField(
            text = text,
            onTextChange = { text = it },
            hint = "Hint",
        )
    }
}
