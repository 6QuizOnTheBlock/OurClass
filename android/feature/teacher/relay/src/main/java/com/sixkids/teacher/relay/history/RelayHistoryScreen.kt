package com.sixkids.teacher.relay.history

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sixkids.designsystem.component.appbar.UlbanDefaultAppBar
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.appbar.UlbanDetailWithProgressAppBar
import com.sixkids.designsystem.component.item.UlbanRelayItem
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.OrangeDark
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Relay
import com.sixkids.teacher.relay.R
import com.sixkids.ui.util.formatToMonthDayTime
import com.sixkids.designsystem.R as DesignSystemR

private const val TAG = "D107"
@Composable
fun RelayHistoryRoute(
    viewModel: RelayHistoryViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToDetail: (Long) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is RelayHistoryEffect.NavigateToRelayDetail -> navigateToDetail(sideEffect.relayId)
                is RelayHistoryEffect.HandleException -> handleException(
                    sideEffect.throwable,
                    sideEffect.retry
                )
            }
        }
    }

    RelayHistoryScreen(
        uiState = uiState,
        padding = padding,
        relayItems = viewModel.relayHistory?.collectAsLazyPagingItems(),
        navigateToDetail = { relayId ->
            viewModel.navigateToRelayDetail(relayId)
        }
    )
}

@Composable
fun RelayHistoryScreen(
    uiState: RelayHistoryState = RelayHistoryState(),
    padding: PaddingValues = PaddingValues(0.dp),
    relayItems: LazyPagingItems<Relay>? = null,
    navigateToDetail: (Long) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(padding)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val currentRelay = uiState.runningRelay
            if (currentRelay == null) {
                UlbanDefaultAppBar(
                    leftIcon = DesignSystemR.drawable.relay,
                    title = stringResource(R.string.relay),
                    content = stringResource(R.string.relay_no),
                    color = Orange,
                    onclick = {},
                    expanded = !isScrolled
                )
            } else {
                UlbanDetailWithProgressAppBar(
                    leftIcon = DesignSystemR.drawable.relay,
                    title = stringResource(R.string.relay),
                    content = stringResource(R.string.relay_current),
                    topDescription = stringResource(
                        R.string.relay_current_runner,
                        uiState.runningRelay.curMemberNickname
                    ),
                    bottomDescription = stringResource(
                        R.string.relay_progress,
                        uiState.runningRelay.totalMemberCount,
                        uiState.runningRelay.doneMemberCount
                    ),
                    totalCnt = uiState.runningRelay.totalMemberCount,
                    successCnt = uiState.runningRelay.doneMemberCount,
                    color = Orange,
                    progressBarColor = OrangeDark,
                    expanded = !isScrolled,
                    onclick = {}
                )
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = stringResource(
                        id = R.string.relay_relay_count,
                        uiState.totalRelayCount
                    ),
                    style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                if (relayItems == null || relayItems.itemCount == 0) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = stringResource(R.string.relay_no_history),
                        style = UlbanTypography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        items(relayItems.itemCount) { index ->
                            relayItems[index]?.let { relay ->
                                UlbanRelayItem(
                                    startDate = relay.startTime,
                                    endDate = relay.endTime,
                                    userCount = relay.lastTurn,
                                    lastMemberName = relay.lastMemberName,
                                    onClick = {navigateToDetail(relay.id)}
                                )
                            }
                        }
                    }
                }
            }
        }
        if (uiState.isLoading) {
            LoadingScreen()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RelayHistoryScreenPreview() {
    UlbanTheme {
        RelayHistoryScreen()
    }
}