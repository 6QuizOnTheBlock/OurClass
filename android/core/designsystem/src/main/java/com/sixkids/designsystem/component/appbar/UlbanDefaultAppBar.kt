package com.sixkids.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.AppBarTypography
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun UlbanDefaultAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int,
    title: String,
    content: String,
    body: String = "",
    color: Color,
    expanded: Boolean = true,
    onclick: () -> Unit = {},
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = leftIcon,
        title = title,
        content = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Text(
                        text = content, style = UlbanTypography.titleLarge,
                    )
                    if (body.isNotEmpty()) {
                        Text(
                            text = body, style = UlbanTypography.bodyLarge,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(3f))
            }
        },
        color = color,
        expanded = expanded,
        onclick = onclick
    )
}
