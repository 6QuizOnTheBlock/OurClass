package com.sixkids.teacher.home.rank

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.model.MemberRankItem
import com.sixkids.teacher.home.R
import com.sixkids.teacher.home.rank.component.RankItem
import com.sixkids.teacher.home.rank.component.RankViewModel
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun RankRoute(
    viewModel: RankViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is RankEffect.onShowSnackBar -> onShowSnackBar(SnackbarToken(it.message))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getOrganizationName()
        viewModel.getClassRank()
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        RankScreen(
            rankState = uiState
        )
        if (uiState.isLoading) {
            LoadingScreen()
        }
    }

}

@Composable
fun RankScreen(
    modifier: Modifier = Modifier,
    rankState: RankState = RankState()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        UlbanDetailAppBar(
            leftIcon = UlbanRes.drawable.rank,
            title = stringResource(id = R.string.teacher_home_rank),
            content = stringResource(id = R.string.teacher_home_rank),
            topDescription = "",
            bottomDescription = rankState.classString,
            color = Yellow
        )
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            items(rankState.rankList.size) { index ->
                RankItem(
                    rank = rankState.rankList[index].rank,
                    name = rankState.rankList[index].name,
                    exp = rankState.rankList[index].exp
                )
                if (index != rankState.rankList.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = Color.Black,
                        thickness = 2.dp
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RankScreenPreview() {
    RankScreen(
        rankState = RankState(
            classString = "구미 초등학교 1학년 1반",
            rankList = listOf(
                MemberRankItem(
                    rank = 1,
                    name = "김철수",
                    exp = 100
                ),
                MemberRankItem(
                    rank = 2,
                    name = "박영희",
                    exp = 90
                ),
                MemberRankItem(
                    rank = 3,
                    name = "이영수",
                    exp = 80
                ),
                MemberRankItem(
                    rank = 4,
                    name = "최영희",
                    exp = 70
                ),
                MemberRankItem(
                    rank = 5,
                    name = "홍길동",
                    exp = 60
                ),
                MemberRankItem(
                    rank = 6,
                    name = "김철수",
                    exp = 50
                ),
                MemberRankItem(
                    rank = 7,
                    name = "박영희",
                    exp = 40
                ),
                MemberRankItem(
                    rank = 8,
                    name = "이영수",
                    exp = 30
                ),
                MemberRankItem(
                    rank = 9,
                    name = "최영희",
                    exp = 20
                ),
                MemberRankItem(
                    rank = 10,
                    name = "홍길동",
                    exp = 10
                )
            )
        )
    )
}