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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.component.card.ContentAligment
import com.sixkids.designsystem.theme.component.card.ContentCard
import com.sixkids.teacher.board.R
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun BoardMainRoute(
    padding: PaddingValues,
    navigateToPost: () -> Unit,
    navigateToChatting: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        BoardMainScreen(
            postCardOnClick = navigateToPost,
            navigateToChatting = navigateToChatting
        )
    }
}

@Composable
fun BoardMainScreen(
    modifier: Modifier = Modifier,
    boardMainState: BoardMainState = BoardMainState(),
    postCardOnClick: () -> Unit = {},
    navigateToChatting: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.board_main_title),
            style = UlbanTypography.titleLarge
        )
        Text(
            text = boardMainState.classString,
            style = UlbanTypography.bodySmall
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.padding(start = 40.dp),
            imageModifier = Modifier.rotate(-20f),
            contentName = stringResource(id = R.string.board_main_announce),
            contentImageId = UlbanRes.drawable.announce,
            cardColor = Orange,
            contentAligment = ContentAligment.ImageStart_TextEnd
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.padding(end = 40.dp),
            contentName = stringResource(id = R.string.board_main_post),
            contentImageId = UlbanRes.drawable.board,
            cardColor = Blue,
            contentAligment = ContentAligment.ImageEnd_TextStart,
            onclick = postCardOnClick
        )
        Spacer(modifier = Modifier.height(20.dp))
        ContentCard(
            modifier = Modifier.padding(start = 40.dp),
            contentName = stringResource(id = R.string.board_main_chat),
            contentImageId = UlbanRes.drawable.chat,
            cardColor = Orange,
            contentAligment = ContentAligment.ImageStart_TextEnd,
            onclick = navigateToChatting
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BoardMainScreenPreview() {
    BoardMainScreen(
        boardMainState = BoardMainState(classString = "인동초등학교 1학년 1반")
    )
}
