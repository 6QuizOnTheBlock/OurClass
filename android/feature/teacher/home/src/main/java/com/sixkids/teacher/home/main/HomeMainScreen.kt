package com.sixkids.teacher.home.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.component.card.ContentAligment
import com.sixkids.designsystem.theme.component.card.ContentCard
import com.sixkids.designsystem.theme.component.card.RankCard
import com.sixkids.teacher.home.component.TeacherInfo

@Composable
fun HomeMainRoute(
    padding: PaddingValues,
    navigateToRank: () -> Unit,
    navigateToChallenge: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        HomeMainScreen(
            navigateToRank = navigateToRank,
            navigateToChallenge = navigateToChallenge
        )
    }
}

@Composable
fun HomeMainScreen(
    modifier: Modifier = Modifier,
    homeMainState: HomeMainState = HomeMainState(),
    navigateToRank: () -> Unit = {},
    navigateToChallenge: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
    ) {
        TeacherInfo(teacherName = "홍유준")
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.fillMaxWidth(),
            contentAligment = ContentAligment.ImageEnd_TextStart,
            cardColor = Cream,
            contentName = "이어 달리기",
            contentImageId = R.drawable.relay,
            runningState = homeMainState.runningRelayState,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.fillMaxWidth(),
            contentAligment = ContentAligment.ImageStart_TextEnd,
            cardColor = Red,
            contentName = "함께 달리기",
            contentImageId = R.drawable.hifive,
            runningState = homeMainState.runningTogetherState,
            onclick = navigateToChallenge
        )
        RankCard(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            onClick = navigateToRank
        )
    }
}


@Composable
@Preview(showBackground = true)
fun HomeMainScreenPreview() {
    UlbanTheme {
        HomeMainScreen()
    }
}
