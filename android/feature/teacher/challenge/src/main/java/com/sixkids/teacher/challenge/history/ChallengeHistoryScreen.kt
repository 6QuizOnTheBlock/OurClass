package com.sixkids.teacher.challenge.history

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import com.sixkids.designsystem.component.appbar.UlbanDetailWithProgressAppBar
import com.sixkids.designsystem.component.item.UlbanChallengeItem
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Challenge
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.util.formatToMonthDayTime

@Composable
fun ChallengeRoute(
    viewModel: ChallengeHistoryViewModel = hiltViewModel(),
    navigateToDetail: (Long, Long?) -> Unit,
    navigateToCreate: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ChallengeHistoryEffect.NavigateToChallengeDetail -> navigateToDetail(
                    sideEffect.challengeId,
                    sideEffect.groupId
                )

                ChallengeHistoryEffect.NavigateToCreateChallenge -> navigateToCreate()
                is ChallengeHistoryEffect.HandleException -> handleException(
                    sideEffect.throwable,
                    sideEffect.retry
                )
            }
        }
    }

    ChallengeHistoryScreen(
        uiState = uiState,
        challengeItems = viewModel.challengeHistory?.collectAsLazyPagingItems(),
        navigateToDetail = { challengeId ->
            viewModel.navigateChallengeDetail(challengeId)
        },
        navigateToCreate = navigateToCreate,
        updateTotalCount = viewModel::updateTotalCount
    )
}

@Composable
fun ChallengeHistoryScreen(
    uiState: ChallengeHistoryState = ChallengeHistoryState(),
    challengeItems: LazyPagingItems<Challenge>? = null,
    navigateToDetail: (Long) -> Unit = {},
    navigateToCreate: () -> Unit = {},
    updateTotalCount: (Int) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val currentChallenge = uiState.runningChallenge
        if (currentChallenge == null) {
            UlbanDefaultAppBar(
                leftIcon = com.sixkids.designsystem.R.drawable.hifive,
                title = stringResource(id = R.string.hifive_challenge),
                content = stringResource(id = R.string.challenge_create),
                color = Red,
                onclick = navigateToCreate,
                expanded = !isScrolled
            )
        } else {
            UlbanDetailWithProgressAppBar(
                leftIcon = com.sixkids.designsystem.R.drawable.hifive,
                title = stringResource(id = R.string.hifive_challenge),
                content = currentChallenge.title,
                topDescription = "${currentChallenge.startTime.formatToMonthDayTime()} ~ ${currentChallenge.endTime.formatToMonthDayTime()}",
                bottomDescription = currentChallenge.content,
                color = Red,
                onclick = { navigateToDetail(currentChallenge.id) },
                totalCnt = currentChallenge.totalMemberCount,
                successCnt = currentChallenge.doneMemberCount,
                badgeCount = currentChallenge.waitingCount,
                expanded = !isScrolled
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
                    id = R.string.total_challenge_count,
                    uiState.totalChallengeCount
                ),
                style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            if (challengeItems == null) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = R.string.no_challenge_history),
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
                    Log.d("DTEST", "ChallengeHistoryScreen:  challengeItems.itemCount ${challengeItems.itemCount}")
                    items(challengeItems.itemCount) { index ->
                        challengeItems[index]?.let { challenge ->
                            if(index == 0) {
                                Log.d("DTEST", "ChallengeHistoryScreen: updateTotalCount ${challenge.totalCount}")
                                updateTotalCount(challenge.totalCount)
                            }
                            UlbanChallengeItem(
                                title = challenge.title,
                                description = challenge.content,
                                startDate = challenge.startTime,
                                endDate = challenge.endTime,
                                userCount = challenge.headCount,
                                onClick = { navigateToDetail(challenge.id) }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyPageDefaultScreenPreview() {
    UlbanTheme {
        ChallengeHistoryScreen()
    }
}
