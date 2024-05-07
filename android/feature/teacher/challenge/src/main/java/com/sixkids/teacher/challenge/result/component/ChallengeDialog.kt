package com.sixkids.teacher.challenge.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.dialog.UlbanBasicDialog
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R

@Composable
fun ChallengeDialog(
    title: String,
    content: String,
    startTime: String,
    endTime: String,
    point: String,
    onConfirmClick: () -> Unit = {}
) {
    UlbanBasicDialog(
        modifier = Modifier.padding(16.dp),
        onDismiss =  onConfirmClick
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
                content = startTime
            )
            ChallengeBody(
                title = stringResource(R.string.end_time),
                content = endTime
            )
            ChallengeBody(
                title = stringResource(R.string.point),
                content = point
            )
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
            style = UlbanTypography.titleSmall,
            color = Gray
        )
        Text(
            text = content,
            style = UlbanTypography.titleSmall
        )
    }
}
