package com.sixkids.student.relay.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.screen.UlbanTopSection
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.relay.R
import com.sixkids.designsystem.R as DesignSystemR
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun RelayCreateRoute(
    viewModel: RelayCreateViewModel = hiltViewModel(),
    navigateToRelayResult: () -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is RelayCreateEffect.NavigateToRelayResult -> navigateToRelayResult()
            is RelayCreateEffect.OnShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    RelayCreateScreen(
        onNewRelayClick = viewModel::newRelayClick,
        onBackClick = onBackClick,
    )

}

@Composable
fun RelayCreateScreen(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    onNewRelayClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UlbanTopSection(stringResource(id = R.string.relay_create_topsection), onBackClick)

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = DesignSystemR.drawable.relay),
                contentDescription = "relay",
                modifier = Modifier.padding(bottom = 20.dp).size(250.dp)
            )

            Text(text = "새로운 이어 달리기를 만듭니다!", style = UlbanTypography.titleMedium)

            Spacer(modifier = Modifier.weight(2f))

            UlbanFilledButton(
                text = stringResource(R.string.relay_create_create),
                onClick = { onNewRelayClick() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RelayCreateScreenPreview() {
    UlbanTheme {
        RelayCreateScreen()
    }
}