package com.sixkids.designsystem.component.textfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography


@Composable
fun UlbanPointTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    maxLines: Int = 1,
    minLines: Int = 1,
    onTextChange: (String) -> Unit = {},
    hint: String = "",
    textStyle: TextStyle = UlbanTypography.bodyLarge,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val pointUnitString = stringResource(R.string.point_unit)
    UlbanBasicTextField(
        modifier = modifier,
        text = text,
        hint = hint,
        textStyle = textStyle,
        onTextChange = onTextChange,
        maxLines = maxLines,
        minLines = minLines,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = keyboardActions,
        visualTransformation = PointVisualTransformation(pointUnitString),
    )
}


@Preview
@Composable
fun UlbanPointTextFieldPreview() {
    UlbanTheme {
        var price by remember { mutableStateOf("") }
        UlbanPointTextField(
            text = price,
            onTextChange = { price = it },
            hint = "Hint",
        )
    }
}
