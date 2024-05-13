package com.sixkids.designsystem.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun RelayPassResultScreen(
    paddingValues: PaddingValues = PaddingValues(horizontal = 30.dp, vertical = 40.dp),
    isSender: Boolean = true,
    title: String = stringResource(R.string.relay_pass_result_title),
    bodyTop: String = stringResource(R.string.relay_pass_result_body_top_receiver),
    bodyMiddle: String,
    onClick: () -> Unit = {}
) {
    val subTitle =
        if (isSender) stringResource(R.string.relay_pass_result_subtitle_sender)
        else stringResource(R.string.relay_pass_result_subtitle_receiver)


    val bodyBottom =
        if (isSender) stringResource(R.string.relay_pass_result_body_bottom_sender)
        else stringResource(R.string.relay_pass_result_body_bottom_receiver)

    val screenWidthDp = with(LocalDensity.current) {
        LocalContext.current.resources.displayMetrics.widthPixels.toDp()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        Text(
            text = title,
            style = UlbanTypography.titleMedium,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        Text(text = subTitle, style = UlbanTypography.bodyMedium)

        Spacer(modifier = Modifier.height(100.dp))

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                Box(
                    modifier = Modifier
                        .size(screenWidthDp / 2)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Orange)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.relay_success),
                    contentDescription = "success",
                    modifier = Modifier
                        .size(screenWidthDp / 2 + screenWidthDp / 7)
                        .offset(y = -screenWidthDp / 10)
                        .align(Alignment.Center)
                )
            }
        }

        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bodyTop,
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 30.dp)
            )

            Text(
                text = bodyMiddle,
                style = UlbanTypography.titleMedium.copy(lineHeight = 30.sp),
                modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = bodyBottom,
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 30.dp)
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        UlbanFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.relay_pass_result_button_done),
            onClick = { onClick() }
        )

    }
}

@Composable
@Preview(showBackground = true)
fun RelayPassResultScreenPreview() {
    UlbanTheme {
        RelayPassResultScreen(
            bodyTop = "오하빈 학생이",
            bodyMiddle = "\"우리반에서 가장 공부를 잘 하는 친구는 누구야?\"",
        )
    }
}