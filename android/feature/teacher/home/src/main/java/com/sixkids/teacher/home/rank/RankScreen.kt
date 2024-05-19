package com.sixkids.teacher.home.rank

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.appbar.UlbanDefaultAppBar
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Gray
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
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        UlbanDefaultAppBar(
            leftIcon = UlbanRes.drawable.rank,
            title = stringResource(id = R.string.teacher_home_rank),
            content = stringResource(id = R.string.teacher_home_rank),
            body = rankState.classString.replace("\n", " "),
            color = Yellow,
            expanded = !isScrolled
        )
        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
            state = listState
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
                        color = Gray,
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