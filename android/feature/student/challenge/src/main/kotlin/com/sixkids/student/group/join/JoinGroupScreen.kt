package com.sixkids.student.group.join

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimple
import com.sixkids.student.challenge.R
import com.sixkids.student.group.component.MemberIcon
import com.sixkids.student.group.component.MultiLayeredCircles
import com.sixkids.ui.extension.collectWithLifecycle


@Composable
fun JoinGroupRoute(
    viewModel: JoinGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.connectSse()
        viewModel.loadUserInfo()
        viewModel.startAdvertise()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopAdvertise()
        }
    }

    viewModel.sideEffect.collectWithLifecycle {

    }
    JoinGroupScreen(uiState)
}

@Composable
fun JoinGroupScreen(
    uiState: JoinGroupState = JoinGroupState()
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.waint_invite),
            style = UlbanTypography.titleSmall,
            modifier = Modifier.padding(top = 32.dp)
        )
        Box(modifier = Modifier.weight(1f)) {
            MultiLayeredCircles(modifier = Modifier.align(Alignment.Center))
            MemberIcon(member = uiState.member, modifier = Modifier.align(Alignment.Center))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun JoinGroupScreenPreview() {
    JoinGroupScreen(
        uiState = JoinGroupState(
            isLoading = false,
            member = MemberSimple(
                id = 1,
                name = "김철수",
                photo = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
            )
        )
    )
}
