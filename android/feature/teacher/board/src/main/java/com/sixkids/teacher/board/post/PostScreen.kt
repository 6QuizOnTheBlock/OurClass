package com.sixkids.teacher.board.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.EditFAB
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Post
import com.sixkids.teacher.board.R
import com.sixkids.teacher.board.post.component.PostItem
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.util.formatToMonthDayTime
import com.sixkids.ui.util.formatToMonthDayTimeKorean
import java.time.LocalDateTime
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun PostRoute(
    viewModel: PostViewModel = hiltViewModel(),
    navigateToDetail: (postId:Long) -> Unit,
    navigateToWrite: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    padding: PaddingValues
) {
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getPostList()
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                PostEffect.NavigateToPostDetail -> {}
                PostEffect.NavigateToWritePost -> {}
                is PostEffect.OnShowSnackBar -> {
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
        PostScreen(
            postState = uiState,
            postItems = viewModel.postList?.collectAsLazyPagingItems(),
            postItemOnclick = navigateToDetail,
            fabClick = navigateToWrite
        )
    }
}


@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    postState: PostState = PostState(),
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
                leftIcon = UlbanRes.drawable.board,
                title = stringResource(id = R.string.board_main_post),
                content = stringResource(id = R.string.board_main_post),
                topDescription = "",
                bottomDescription = "",
                color = Blue
            )
            
            if (postItems == null){
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.board_post_no_items),
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
                                onClick = { postItemOnclick(post.id) }
                            )
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
            buttonColor = Blue,
            iconColor = BlueDark,
            onClick = fabClick
        )
        if (postState.isLoding){
            LoadingScreen()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PostRoutePreview() {
    PostScreen()
}