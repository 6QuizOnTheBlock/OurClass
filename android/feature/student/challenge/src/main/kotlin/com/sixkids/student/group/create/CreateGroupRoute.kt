package com.sixkids.student.group.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.challenge.R
import com.sixkids.student.group.component.GroupWaiting

@Composable
fun CreateGroupRoute() {
    CreateGroupScreen()
}

@Composable
fun CreateGroupScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.invite_friend),
            style = UlbanTypography.titleSmall,
            modifier = Modifier.padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        GroupWaiting()
    }
}

@Preview(showBackground = true)
@Composable
fun CreateGroupScreenPreview() {
    UlbanTheme {
        CreateGroupScreen()
    }
}
