package com.sixkids.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.AppBarTypography
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun UlbanDetailAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int,
    title: String,
    content: String,
    topDescription: String,
    bottomDescription: String,
    badgeCount: Int = 0,
    color: Color,
    expanded: Boolean = true,
    onclick: () -> Unit = {},
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = leftIcon,
        title = title,
        content = {
            AppBarDetailInfo(
                title = content,
                topDescription = topDescription,
                bottomDescription = bottomDescription,
                badgeCount = badgeCount
            )
        },
        color = color,
        expanded = expanded,
        onclick = onclick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarDetailInfo(
    modifier: Modifier = Modifier,
    title: String,
    topDescription: String,
    bottomDescription: String,
    badgeCount: Int = 0
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = topDescription,
                style = AppBarTypography.bodySmall,
            )
            Spacer(modifier = modifier.weight(1f))
            if (badgeCount > 0) {
                Badge(
                    modifier = Modifier
                        .padding(top = 4.dp, end = 12.dp)
                        .size(32.dp),
                    containerColor = RedDark,
                    contentColor = Color.White
                ) {
                    Text(
                        text = "$badgeCount",
                        style = AppBarTypography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
        Text(
            text = title,
            style = AppBarTypography.titleSmall,
            modifier = Modifier.fillMaxWidth()
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
