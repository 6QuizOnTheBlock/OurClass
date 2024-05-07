package com.sixkids.teacher.challenge.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.component.dialog.UlbanBasicDialog
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.util.formatPoint
import com.sixkids.ui.util.formatToMonthDayTimeKorean
import java.time.LocalDateTime

@Composable
fun ChallengeDialog(
    title: String,
    content: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    point: Int,
    onConfirmClick: () -> Unit = {}
) {
    UlbanBasicDialog(
        modifier = Modifier.padding(16.dp),
        onDismiss = onConfirmClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChallengeBody(
                title = stringResource(id = R.string.title),
                content = title
            )
            ChallengeBody(
                title = stringResource(R.string.content),
                content = content
            )
            ChallengeBody(
                title = stringResource(R.string.start_time),
                content = startTime.formatToMonthDayTimeKorean()
            )
            ChallengeBody(
                title = stringResource(R.string.end_time),
                content = endTime.formatToMonthDayTimeKorean()
            )
            ChallengeBody(
                title = stringResource(R.string.point),
                content = point.formatPoint()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onConfirmClick) {
                Text(
                    text = stringResource(R.string.confirm),
                    style = UlbanTypography.titleSmall,
                    color = Color.Black,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}


@Composable
fun ChallengeBody(
    title: String,
    content: String,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = UlbanTypography.titleSmall.copy(fontSize = 12.sp),
            color = Gray
        )
        Text(
            text = content,
            style = UlbanTypography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultDialogContent() {
    UlbanTheme {
        ChallengeDialog(
            title = "4월 22일 깜짝 미션",
            content = "문화의 날을 맞아 우리반 친구들 3명 이상 모여 영화를 관람해봐요~",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            point = 1000
        )
    }
}
