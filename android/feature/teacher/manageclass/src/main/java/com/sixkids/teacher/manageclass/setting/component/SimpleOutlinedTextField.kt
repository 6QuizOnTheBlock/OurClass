package com.sixkids.teacher.manageclass.setting.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.textfield.UlbanBasicTextField
import com.sixkids.designsystem.component.textfield.UlbanNumberTextField

@Composable
fun SimpleOutlinedTextField(
    modifier: Modifier = Modifier,
    text : String = "",
    hint: String = "",
    onTextChange: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        UlbanBasicTextField(
            modifier = modifier,
            text = text,
            hint = hint,
            onTextChange = onTextChange,
        )
    }
}

@Composable
fun SimpleNumberOutlinedTextField(
    modifier: Modifier = Modifier,
    text : String = "",
    hint: String = "",
    postfix: String = "",
    onTextChange: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier.border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        UlbanNumberTextField(
            modifier = modifier,
            text = text,
            hint = hint,
            onTextChange = onTextChange,
            postfix = postfix
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleOutlinedTextFieldPreview() {
    SimpleOutlinedTextField(
        text = "Hello",
    )
}