package com.sixkids.designsystem.component.checkbox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun TextRadioButton(
    selected: Boolean = false,
    onClick: () -> Unit = { },
    text: String = ""
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 익명 체크박스
        RadioButton(
            modifier = Modifier
                .scale(1.2f)
                .requiredSize(30.dp),
            selected = selected,
            onClick = null,
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
    TextRadioButton(
        text = "라디오버튼"
    )
}
