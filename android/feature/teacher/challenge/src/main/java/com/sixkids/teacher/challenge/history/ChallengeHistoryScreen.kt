package com.sixkids.teacher.challenge.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.sixkids.designsystem.component.appbar.UlbanDefaultAppBar
import com.sixkids.designsystem.component.appbar.UlbanDetailWithProgressAppBar
import com.sixkids.designsystem.component.item.UlbanChallengeItem
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R
import com.sixkids.ui.util.formatToMonthDayTime

@Composable
fun ChallengeHistoryScreen(
    padding: PaddingValues,
    uiState: ChallengeHistoryState = ChallengeHistoryState(),
    navigateToDetail: (Int) -> Unit = {},
    navigateToCreate: () -> Unit = {},
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
            .padding(padding)
    ) {
        val currentChallenge = uiState.currentChallenge
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
                topDescription = "${currentChallenge.startDate.formatToMonthDayTime()} ~ ${currentChallenge.endDate.formatToMonthDayTime()}",
                bottomDescription = currentChallenge.description,
                color = Red,
                onclick = { navigateToDetail(currentChallenge.id) },
                totalCnt = currentChallenge.totalUserCount,
                successCnt = currentChallenge.activeUserCount,
                badgeCount = currentChallenge.pendingChallengeCount,
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
            Divider(modifier = Modifier.padding(vertical = 4.dp))

            if (uiState.challengeHistory.isEmpty()) {
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
                    items(
                        items = uiState.challengeHistory,
                        key = { it.id }
                    ) { challenge ->
                        UlbanChallengeItem(
                            title = challenge.title,
                            description = challenge.description,
                            startDate = challenge.startDate,
                            endDate = challenge.endDate,
                            userCount = challenge.userCount,
                        )
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
        ChallengeHistoryScreen(
            padding = PaddingValues(0.dp),
        )
    }
}
