package com.sixkids.designsystem.component.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.AppBarTypography
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun UlbanDetailAppBar(
    modifier: Modifier = Modifier,
    leftIcon: @Composable () -> Unit = {},
    title: String,
    content: String,
    topDescription: String,
    bottomDescription: String,
    color: Color,
    expanded: Boolean = true,
    onclick: () -> Unit,
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = leftIcon,
        title = title,
        content = {
            AppBarDetailInfo(
                title = content,
                topDescription = topDescription,
                bottomDescription = bottomDescription
            )
        },
        color = color,
        expanded = expanded,
        onclick = onclick
    )
}

@Composable
fun AppBarDetailInfo(
    modifier: Modifier = Modifier,
    title: String,
    topDescription: String,
    bottomDescription: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = topDescription,
            style = AppBarTypography.bodySmall,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = title,
            style = AppBarTypography.titleSmall,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = bottomDescription,
            style = AppBarTypography.bodyMedium,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0XFFFFCCA3)
@Composable
fun UlbanDetailAppBarPreview() {
    UlbanTheme {
        AppBarDetailInfo(
            topDescription = "04.17 15:00~",
            title = "이어 달리기가\n진행 중입니다!",
            bottomDescription = "현재 주자는 오하빈 학생입니다."
        )
    }
}
