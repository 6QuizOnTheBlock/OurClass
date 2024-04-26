package com.sixkids.designsystem.component.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sixkids.designsystem.theme.AppBarTypography

@Composable
fun UlbanDefaultAppBar(
    modifier: Modifier = Modifier,
    leftIcon: @Composable () -> Unit = {},
    title: String,
    content: String,
    color: Color,
    expanded: Boolean = true,
    onclick: () -> Unit,
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = leftIcon,
        title = title,
        content = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = content, style = AppBarTypography.titleLarge,
                )
                Spacer(modifier = Modifier.weight(3f))
            }
        },
        color = color,
        expanded = expanded,
        onclick = onclick
    )
}
