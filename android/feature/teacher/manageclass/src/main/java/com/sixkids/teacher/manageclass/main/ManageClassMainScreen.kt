package com.sixkids.teacher.manageclass.main

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.component.card.ContentAligment
import com.sixkids.designsystem.theme.component.card.ContentCard
import com.sixkids.teacher.manageclass.R
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun ManageClassMainRoute(
    padding: PaddingValues,
    viewModel: ManageClassMainViewModel = hiltViewModel(),
    navigateToClassSummary: () -> Unit,
    navigateToClassSetting: () -> Unit,
    navigateToChattingFilter: () -> Unit,
    navigateToInvite: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initData()
    }
    Box(
        modifier = Modifier.padding(padding)
    ) {
        ManageClassMainScreen(
            manageClassMainState = uiState,
            summaryCardOnClick = navigateToClassSummary,
            settingCardOnClick = navigateToClassSetting,
            chattingFilterCardOnClick = navigateToChattingFilter,
            inviteCardOnClick = navigateToInvite
        )
    }
}

@Composable
fun ManageClassMainScreen(
    modifier: Modifier = Modifier,
    manageClassMainState: ManageClassMainState = ManageClassMainState(),
    summaryCardOnClick: () -> Unit = {},
    settingCardOnClick: () -> Unit = {},
    chattingFilterCardOnClick: () -> Unit = {},
    inviteCardOnClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.manage_class_title),
            style = UlbanTypography.titleLarge,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = manageClassMainState.classString.replace("\n", " "),
            style = UlbanTypography.titleSmall
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            cardHeight = 130.dp,
            modifier = Modifier.padding(start = 40.dp),
            imageModifier = Modifier.rotate(60f).padding(10.dp),
            contentName = stringResource(id = R.string.manage_class_statistics),
            contentImageId = UlbanRes.drawable.statistics,
            cardColor = Blue,
            contentAligment = ContentAligment.ImageStart_TextEnd,
            onclick = summaryCardOnClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            cardHeight = 130.dp,
            modifier = Modifier.padding(end = 40.dp),
            imageModifier = Modifier.padding(15.dp),
            contentName = stringResource(id = R.string.manage_class_setting),
            contentImageId = UlbanRes.drawable.setting,
            cardColor = Red,
            contentAligment = ContentAligment.ImageEnd_TextStart,
            onclick = settingCardOnClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            cardHeight = 130.dp,
            modifier = Modifier.padding(start = 40.dp),
            imageModifier = Modifier.padding(15.dp),
            contentName = stringResource(id = R.string.manage_class_filter_chat),
            contentImageId = UlbanRes.drawable.chat_filter,
            cardColor = Yellow,
            contentAligment = ContentAligment.ImageStart_TextEnd,
            onclick = chattingFilterCardOnClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            cardHeight = 130.dp,
            modifier = Modifier.padding(end = 40.dp),
            imageModifier = Modifier.padding(15.dp),
            contentName = stringResource(id = R.string.manage_class_invite),
            contentImageId = UlbanRes.drawable.invite,
            cardColor = Green   ,
            contentAligment = ContentAligment.ImageEnd_TextStart,
            onclick = inviteCardOnClick
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ManageClassMainScreenPreview() {
    ManageClassMainScreen(
        manageClassMainState = ManageClassMainState(classString = "인동초등학교 1학년 1반")
    )
}
