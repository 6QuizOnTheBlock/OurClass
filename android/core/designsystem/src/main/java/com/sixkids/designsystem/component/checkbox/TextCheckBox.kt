package com.sixkids.designsystem.component.checkbox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun TextCheckBox(
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    checkedColor: Color = Blue,
    uncheckedColor: Color = Color.Black,
    text: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 익명 체크박스
        Checkbox(
            modifier = Modifier.scale(1.2f).requiredSize(30.dp),
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = checkedColor,
                uncheckedColor = uncheckedColor
            )
        )
        if(text.isNotEmpty()) {
            Text(
                text = text,
                style = UlbanTypography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextCheckBoxPreview() {
    TextCheckBox(
        text = "체크박스"
    )
}
