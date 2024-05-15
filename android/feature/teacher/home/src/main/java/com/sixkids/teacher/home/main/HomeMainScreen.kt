package com.sixkids.teacher.home.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.component.card.ContentAligment
import com.sixkids.designsystem.theme.component.card.ContentCard
import com.sixkids.designsystem.theme.component.card.ContentVerticalCard
import com.sixkids.teacher.home.component.TeacherInfo

@Composable
fun HomeMainRoute(
    padding: PaddingValues,
    navigateToRank: () -> Unit,
    navigateToChallenge: () -> Unit,
    navigateToRelay: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        HomeMainScreen(
            navigateToRank = navigateToRank,
            navigateToChallenge = navigateToChallenge,
            navigateToRelay = navigateToRelay
        )
    }
}

@Composable
fun HomeMainScreen(
    modifier: Modifier = Modifier,
    homeMainState: HomeMainState = HomeMainState(),
    navigateToRank: () -> Unit = {},
    navigateToChallenge: () -> Unit = {},
    navigateToRelay: () -> Unit = {}
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
            onclick = navigateToRelay
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
        Row {
            ContentVerticalCard(
                cardModifier = Modifier
                    .padding(top = 20.dp, end = 10.dp, bottom = 20.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                cardColor = Yellow,
                imageDrawable = R.drawable.rank,
                text = "랭킹",
                onClick = navigateToRank
            )
            ContentVerticalCard(
                cardModifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, bottom = 20.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                cardColor = Purple,
                imageDrawable = R.drawable.rank,
                text = "퀴즈",
                onClick = { }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun HomeMainScreenPreview() {
    UlbanTheme {
        HomeMainScreen()
    }
}
