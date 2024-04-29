package com.sixkids.teacher.challenge.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.ChallengeDetail
import com.sixkids.model.Group
import com.sixkids.model.MemberSimple
import com.sixkids.model.Report
import com.sixkids.teacher.challenge.R
import com.sixkids.teacher.challenge.detail.component.UlbanReportItem
import com.sixkids.ui.util.formatToMonthDayTime
import java.time.LocalDateTime


@Composable
fun ChallengeDetailScreen(
    padding: PaddingValues,
    uiState: ChallengeDetailState = ChallengeDetailState(),
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
        UlbanDetailAppBar(
            leftIcon = com.sixkids.designsystem.R.drawable.hifive,
            title = stringResource(id = R.string.hifive_challenge),
            content = uiState.challengeDetail.title,
            topDescription = "${uiState.challengeDetail.startDate.formatToMonthDayTime()} ~ ${uiState.challengeDetail.endDate.formatToMonthDayTime()}",
            bottomDescription = uiState.challengeDetail.description,
            color = Red,
            expanded = !isScrolled,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(40.dp),
                    painter = painterResource(id = com.sixkids.designsystem.R.drawable.member),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                Text(
                    text = stringResource(
                        id = R.string.challenge_report_state,
                        uiState.challengeDetail.teamCount,
                        uiState.challengeDetail.userCount
                    ),
                    style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Divider(modifier = Modifier.padding(vertical = 4.dp))

            if (uiState.challengeDetail.reportList.isEmpty()) {
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
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(uiState.challengeDetail.reportList) { report ->
                        UlbanReportItem(
                            report = report,
                            onAccept = { },
                            onReject = { }
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ChallengeDetailScreenPreview() {
    UlbanTheme {
        ChallengeDetailScreen(
            padding = PaddingValues(0.dp),
            uiState = ChallengeDetailState(
                challengeDetail = ChallengeDetail(
                    title = "4월 22일 함께 달리기",
                    description = "문화의 날을 맞아 우리반 친구들 3명이상 만나서 영화를 보자!",
                    startDate = LocalDateTime.now(),
                    endDate = LocalDateTime.now(),
                    reportList = List(10) {
                        Report(
                            content = "4명 다 모여서 쿵푸팬더 4 다같이 봤어요!!",
                            startDate = LocalDateTime.now(),
                            endDate = LocalDateTime.now(),
                            accepted = it % 2 == 0,
                            file = "https://file2.nocutnews.co.kr/newsroom/image/2024/04/05/202404052218304873_0.jpg",
                            group = Group(
                                leaderId = 1,
                                studentList = listOf(
                                    MemberSimple(
                                        id = 1,
                                        name = "김규리",
                                        photo = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSGfpQ3m-QWiXgCBJJbrcUFdNdWAhj7rcUqjeNUC6eKcXZDAtWm"
                                    ),
                                    MemberSimple(
                                        id = 2,
                                        name = "오하빈",
                                        photo = "https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg"
                                    ),
                                    MemberSimple(
                                        id = 3,
                                        name = "차성원",
                                        photo = "https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/E172/production/_126241775_getty_cats.png"
                                    ),
                                    MemberSimple(
                                        id = 4,
                                        name = "정철주",
                                        photo = "https://image.newsis.com/2023/07/12/NISI20230712_0001313626_web.jpg?rnd=20230712163021"
                                    )
                                )
                            )
                        )
                    }
                )
            )
        )
    }
}
