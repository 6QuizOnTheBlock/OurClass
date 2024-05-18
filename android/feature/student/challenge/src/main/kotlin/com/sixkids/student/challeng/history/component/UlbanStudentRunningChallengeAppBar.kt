package com.sixkids.student.challeng.history.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.component.appbar.AppBarDetailInfo
import com.sixkids.designsystem.component.appbar.BasicAppBar
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.challenge.R

@Composable
fun UlbanStudentRunningChallengeAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int,
    title: String,
    content: String,
    topDescription: String,
    bottomDescription: String,
    teamDescription: String,
    runningTimeDescription: String,
    color: Color,
    expanded: Boolean = true,
    onReportEnable: Boolean = true,
    onReportClick: () -> Unit,
    onClick: () -> Unit = {}

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
                if(onReportEnable){
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        UlbanFilledButton(
                            text = stringResource(R.string.end),
                            modifier = Modifier.padding(end = 16.dp),
                            color = RedDark,
                            textColor = Cream,
                            textStyle = UlbanTypography.titleSmall.copy(fontSize = 12.sp),
                            onClick = onReportClick
                        )
                    }
                }

                AppBarDetailInfo(
                    title = content,
                    topDescription = topDescription,
                    bottomDescription = bottomDescription,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.team_member),
                    style = UlbanTypography.titleSmall.copy(fontSize = 12.sp)
                )
                Text(
                    text = teamDescription,
                    style = UlbanTypography.bodySmall
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = runningTimeDescription,
                    style = UlbanTypography.titleSmall
                )
            }
        },
        color = color,
        expanded = expanded,
        onclick = onClick
    )
}


@Preview(showBackground = true)
@Composable
private fun UlbanStudentRunningChallengeAppBarPreview() {
    UlbanStudentRunningChallengeAppBar(
        leftIcon = com.sixkids.designsystem.R.drawable.hifive,
        title = "title",
        content = "4월 22일 함께 달리기",
        topDescription = "04.17 15:00 ~ 04.19 20:00",
        bottomDescription = "문화의 날을 맞아 우리반 친구들 4명 이상 만나서 영화를 보자",
        color = Red,
        teamDescription = "4명 이상 자율적으로 구성해 봐요",
        runningTimeDescription = "1시간 20분째 진행 중입니다",
        onReportClick = {}
    )
}
