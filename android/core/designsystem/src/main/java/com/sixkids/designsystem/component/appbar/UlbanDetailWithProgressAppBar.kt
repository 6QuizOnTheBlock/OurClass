package com.sixkids.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.component.progressbar.StudentProgressBar

@Composable
fun UlbanDetailWithProgressAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int,
    title: String,
    content: String,
    topDescription: String,
    bottomDescription: String,
    badgeCount: Int = 0,
    totalCnt: Int,
    successCnt: Int,
    color: Color,
    progressBarColor: Color = RedDark,
    expanded: Boolean = true,
    onclick: () -> Unit,
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = leftIcon,
        title = title,
        content = {
            Column(
                modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                AppBarDetailInfo(
                    title = content,
                    topDescription = topDescription,
                    bottomDescription = bottomDescription,
                    badgeCount = badgeCount
                )
                StudentProgressBar(
                    modifier = modifier.padding(top = 8.dp, end = 16.dp),
                    totalStudentCount = totalCnt,
                    successStudentCount = successCnt,
                    progressBarColor = progressBarColor
                )
            }
        },
        color = color,
        expanded = expanded,
        onclick = onclick
    )
}
