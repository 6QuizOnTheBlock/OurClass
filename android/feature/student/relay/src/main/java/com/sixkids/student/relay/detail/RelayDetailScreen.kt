package com.sixkids.student.relay.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.item.UlbanRunnerItem
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.relay.R
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.ui.util.formatToMonthDayTime
import com.sixkids.designsystem.R as DesignSystemR


@Composable
fun RelayDetailRoute(
    viewModel: RelayDetailViewModel = hiltViewModel(),
    handleException: (Throwable, () -> Unit) -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is RelayDetailSideEffect.HandleException -> handleException(it.throwable, it.retry)
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getRelayDetail()
    }

    RelayDetailScreen(
        uiState = uiState
    )

}

@Composable
fun RelayDetailScreen(
    uiState: RelayDetailState = RelayDetailState()
) {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100
        }
    }

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            UlbanDetailAppBar(
                leftIcon = DesignSystemR.drawable.relay,
                title = stringResource(id = R.string.relay_challenge),
                content = stringResource(id = R.string.relay_challenge),
                topDescription = "${uiState.relayDetail.startTime.formatToMonthDayTime()} ~ ${uiState.relayDetail.endTime.formatToMonthDayTime()}",
                bottomDescription = stringResource(
                    id = R.string.relay_detail_last_member,
                    uiState.relayDetail.lastMemberName
                ),
                color = Orange,
                expanded = !isScrolled,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(32.dp),
                        painter = painterResource(id = DesignSystemR.drawable.member),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(
                            id = R.string.relay_detail_total_count,
                            uiState.relayDetail.lastTurn
                        ),
                        style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                if (uiState.relayDetail.runnerList.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.relay_no_history),
                        style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                } else {
                    LazyColumn(
                        state = listState,
                    ) {
                        items(uiState.relayDetail.runnerList) { runner ->
                            UlbanRunnerItem(
                                memberPhoto = runner.memberPhoto,
                                memberName = runner.memberName,
                                time = runner.time,
                                question = runner.question,
                                isLastTurn = runner.endStatus
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RelayDetailScreenPreview() {
    UlbanTheme {
        RelayDetailScreen()
    }
}
