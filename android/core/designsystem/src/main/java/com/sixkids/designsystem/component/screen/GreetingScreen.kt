package com.sixkids.designsystem.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun GreetingScreen(
    isSender: Boolean = true,
    onClick: () -> Unit = {}
) {
    val title =
        if (isSender) "친구에게 인사 보내기"
        else "친구의 인사 받기"


    val body =
        if (isSender) stringResource(R.string.relay_tagging_body_sender)
        else stringResource(R.string.relay_tagging_body_receiver)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = title,
            style = UlbanTypography.titleMedium,
            modifier = Modifier.padding(30.dp, 40.dp, 30.dp, 15.dp)
        )


        Spacer(modifier = Modifier.height(100.dp))

        Image(painter = painterResource(id = R.drawable.relay_tag), contentDescription = "tagging",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )


        Text(
            text = body, style = UlbanTypography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(1f))
        if (isSender) {
            UlbanFilledButton(
                text = "완료",
                onClick = { onClick() },
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 40.dp)
                    .fillMaxWidth()
            )
        }
    }
}