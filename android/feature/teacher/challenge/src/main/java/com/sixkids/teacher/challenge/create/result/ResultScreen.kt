package com.sixkids.teacher.challenge.create.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.card.UlbanMissionCard
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R

@Composable
fun ResultRoute() {

    ResultScreen()
}


@Composable
fun ResultScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.new_challenge_created),
            style = UlbanTypography.titleSmall,
            textAlign = TextAlign.Center
        )
        UlbanMissionCard(
            title = "챌린지",
            subTitle = "챌린지 설명",
            onClick = {}
        )

        UlbanFilledButton(text = "닫기", onClick = { /*TODO*/ })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultContent() {
    UlbanTheme {
        ResultScreen()
    }
}
