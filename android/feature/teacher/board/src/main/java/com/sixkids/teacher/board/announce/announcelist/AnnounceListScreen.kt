package com.sixkids.teacher.board.announce.announcelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.EditFAB
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.OrangeDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Post
import com.sixkids.teacher.board.post.postlist.component.PostItem
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.util.formatToMonthDayTimeKorean

@Composable
fun AnnounceListRoute(
    viewModel: AnnounceListViewModel = hiltViewModel(),
    navigateToAnnounceDetail: (postId:Long) -> Unit,
    navigateToAnnounceWrite: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    padding: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAnnounceList()
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                AnnounceListEffect.NavigateToAnnounceDetail -> {}
                AnnounceListEffect.NavigateToWriteAnnounce -> {}
                is AnnounceListEffect.OnShowSnackBar -> {
                    onShowSnackBar(SnackbarToken(message = sideEffect.message))
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        AnnounceListScreen(
            announceListState = uiState,
            postItems = viewModel.postList?.collectAsLazyPagingItems(),
            postItemOnclick = navigateToAnnounceDetail,
            fabClick = navigateToAnnounceWrite
        )
    }
}

@Composable
fun AnnounceListScreen(
    modifier: Modifier = Modifier,
    announceListState: AnnounceListState = AnnounceListState(),
    postItems: LazyPagingItems<Post>? = null,
    postItemOnclick: (postId: Long) -> Unit = {},
    fabClick: () -> Unit = {}
) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(

        ) {
            UlbanDetailAppBar(
                leftIcon = R.drawable.announce,
                title = stringResource(id = com.sixkids.teacher.board.R.string.board_main_announce),
                content = stringResource(id = com.sixkids.teacher.board.R.string.board_main_announce),
                topDescription = "",
                bottomDescription = announceListState.classString,
                color = Orange
            )

            if (postItems != null){
                if (postItems.itemCount == 0){
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = com.sixkids.teacher.board.R.string.board_announce_no_item),
                        textAlign = TextAlign.Center,
                        style = UlbanTypography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        state = listState,
                    ) {
                        items(postItems.itemCount) { index ->
                            postItems[index]?.let { post ->
                                PostItem(
                                    title = post.title,
                                    writer = post.writer,
                                    dateString = post.time.formatToMonthDayTimeKorean(),
                                    commentCount = post.commentCount,
                                    dividerColor = OrangeDark,
                                    onClick = { postItemOnclick(post.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
        //FAB
        EditFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            buttonColor = Orange,
            iconColor = OrangeDark,
            onClick = fabClick
        )
        if (announceListState.isLoding){
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnnounceListScreenPreview() {
    AnnounceListScreen()
}