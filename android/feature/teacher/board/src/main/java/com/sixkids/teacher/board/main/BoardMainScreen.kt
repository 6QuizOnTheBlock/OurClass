package com.sixkids.teacher.board.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.sixkids.designsystem.theme.BlueText
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.OrangeText
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.PurpleText
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.component.card.ContentAligment
import com.sixkids.designsystem.theme.component.card.ContentCard
import com.sixkids.teacher.board.R
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun BoardMainRoute(
    viewModel: BoardMainViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToPost: () -> Unit,
    navigateToChatting: () -> Unit,
    navigateToAnnounce: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        BoardMainScreen(
            boardMainState = uiState,
            postCardOnClick = navigateToPost,
            navigateToChatting = navigateToChatting,
            announceCardOnClick = navigateToAnnounce
        )
    }
}

@Composable
fun BoardMainScreen(
    modifier: Modifier = Modifier,
    boardMainState: BoardMainState = BoardMainState(),
    postCardOnClick: () -> Unit = {},
    navigateToChatting: () -> Unit = {},
    announceCardOnClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.board_main_title),
            style = UlbanTypography.titleLarge,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = boardMainState.classString.replace("\n", " "),
            style = UlbanTypography.titleSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        ContentCard(
            modifier = Modifier.padding(start = 40.dp),
            imageModifier = Modifier.rotate(-20f).padding(15.dp),
            contentName = stringResource(id = R.string.board_main_announce),
            contentImageId = UlbanRes.drawable.announce,
            cardColor = Orange,
            textColor = OrangeText,
            contentAligment = ContentAligment.ImageStart_TextEnd,
            onclick = announceCardOnClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.padding(end = 40.dp),
            imageModifier = Modifier.padding(15.dp),
            contentName = stringResource(id = R.string.board_main_post),
            contentImageId = UlbanRes.drawable.board,
            cardColor = Blue,
            textColor = BlueText,
            contentAligment = ContentAligment.ImageEnd_TextStart,
            onclick = postCardOnClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.padding(start = 40.dp),
            imageModifier = Modifier.padding(10.dp),
            contentName = stringResource(id = R.string.board_main_chat),
            contentImageId = UlbanRes.drawable.chat,
            cardColor = Purple,
            textColor = PurpleText,
            contentAligment = ContentAligment.ImageStart_TextEnd,
            onclick = navigateToChatting
        )
        Spacer(modifier = Modifier.weight(1.2f))
    }
}


@Preview(showBackground = true)
@Composable
fun BoardMainScreenPreview() {
    BoardMainScreen(
        boardMainState = BoardMainState(classString = "인동초등학교 1학년 1반")
    )
}
