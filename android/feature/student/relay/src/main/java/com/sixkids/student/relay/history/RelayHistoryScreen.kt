package com.sixkids.student.relay.history

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
import com.sixkids.designsystem.component.item.UlbanRelayItem
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Relay
import com.sixkids.student.relay.R
import com.sixkids.ui.util.formatToMonthDayTime
import com.sixkids.designsystem.R as DesignSystemR

private const val TAG = "D107"
@Composable
fun RelayRoute(
    viewModel: RelayHistoryViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToDetail: (Long) -> Unit,
    navigateToCreate: () -> Unit,
    navigateToAnswer: (Long) -> Unit,
    navigateToTaggingReceiver: (Long) -> Unit,
    navigateToJoin: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        Log.d(TAG, "RelayRoute: ")
        viewModel.initData()
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is RelayHistoryEffect.NavigateToRelayDetail -> navigateToDetail(sideEffect.relayId)
                is RelayHistoryEffect.NavigateToCreateRelay -> navigateToCreate()
                is RelayHistoryEffect.NavigateToJoinRelay -> navigateToJoin()
                is RelayHistoryEffect.NavigateToAnswerRelay -> navigateToAnswer(sideEffect.relayId)
                is RelayHistoryEffect.NavigateToTaggingReceiverRelay -> navigateToTaggingReceiver(sideEffect.relayId)
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
        },
        navigateToCreate = navigateToCreate,
        navigateToAnswer = { relayId ->
            viewModel.navigateToAnswerRelay(relayId)
        },
        navigateToTaggingReceiver = { relayId ->
            viewModel.navigateToTaggingReceiverRelay(relayId)
        },
        updateTotalCount = {
            viewModel.updateTotalCount(it)
        }
    )
}

@Composable
fun RelayHistoryScreen(
    uiState: RelayHistoryState = RelayHistoryState(),
    padding: PaddingValues = PaddingValues(0.dp),
    relayItems: LazyPagingItems<Relay>? = null,
    navigateToDetail: (Long) -> Unit = {},
    navigateToCreate: () -> Unit = {},
    navigateToAnswer: (Long) -> Unit = {},
    navigateToTaggingReceiver: (Long) -> Unit = {},
    updateTotalCount: (Int) -> Unit = {}
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
                    title = stringResource(R.string.relay_challenge),
                    content = stringResource(R.string.relay_create),
                    color = Orange,
                    onclick = navigateToCreate,
                    expanded = !isScrolled
                )
            } else {
                UlbanDetailAppBar(
                    leftIcon = DesignSystemR.drawable.relay,
                    title = stringResource(R.string.relay_challenge),
                    content = if (currentRelay.myTurnStatus) stringResource(R.string.relay_running_myturn) else stringResource(
                        R.string.relay_running_not_myturn
                    ),
                    topDescription = "${currentRelay.startTime.formatToMonthDayTime()} ~",
                    bottomDescription = if (currentRelay.myTurnStatus) stringResource(R.string.relay_answer_myturn) else stringResource(
                        R.string.relay_answer_not_myturn
                    ),
                    color = Orange,
                    onclick = { if (currentRelay.myTurnStatus) navigateToAnswer(currentRelay.id) else navigateToTaggingReceiver(currentRelay.id) },
                    expanded = !isScrolled,
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
                                if (index == 0)
                                    updateTotalCount(relay.totalCount)
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