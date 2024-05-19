package com.sixkids.teacher.home.main

import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.OrangeDark
import com.sixkids.designsystem.theme.OrangeText
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.PurpleText
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.RedText
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.YellowText
import com.sixkids.designsystem.theme.component.card.ContentAligment
import com.sixkids.designsystem.theme.component.card.ContentCard
import com.sixkids.designsystem.theme.component.card.ContentVerticalCard
import com.sixkids.teacher.home.component.TeacherInfo
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun HomeMainRoute(
    viewModel: HomeMainViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToRank: () -> Unit,
    navigateToChallenge: () -> Unit,
    navigateToRelay: () -> Unit,
    navigateToQuiz: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is HomeMainEffect.NavigateToRanking -> navigateToRank()
        }
    }


    LaunchedEffect(Unit) {
        viewModel.loadUserInfo()
        viewModel.loadSelectedOrganizationName()
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        HomeMainScreen(
            homeMainState = uiState,
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
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .verticalScroll(ScrollState(0)),
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        TeacherInfo(
            teacherName = homeMainState.teacherName,
            teacherImageUrl = homeMainState.teacherImageUrl,
            organizationName = homeMainState.classString
        )
        Spacer(modifier = Modifier.weight(1f))
        ContentCard(
            modifier = Modifier.fillMaxWidth(),
            contentAligment = ContentAligment.ImageEnd_TextStart,
            cardColor = Orange,
            textColor = OrangeText,
            contentName = "이어 달리기",
            contentImageId = R.drawable.relay,
            onclick = navigateToRelay
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.fillMaxWidth(),
            contentAligment = ContentAligment.ImageStart_TextEnd,
            cardColor = Red,
            textColor = RedText,
            contentName = "함께 달리기",
            contentImageId = R.drawable.hifive,
            onclick = navigateToChallenge
        )
        Row {
            ContentVerticalCard(
                cardModifier = Modifier
                    .padding(top = 20.dp, end = 10.dp, bottom = 20.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                cardColor = Yellow,
                textColor = YellowText,
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
                textColor = PurpleText,
                imageDrawable = R.drawable.quiz,
                text = "퀴즈",
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
@Preview(showBackground = true)
fun HomeMainScreenPreview() {
    UlbanTheme {
        HomeMainScreen(
            homeMainState = HomeMainState(
                classString = "구미 초등학교 1학년 1반",
                teacherName = "홍유준"
            )
        )
    }
}
