package com.sixkids.student.board.main

import android.util.Log
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
import com.sixkids.designsystem.component.appbar.UlbanDefaultAppBar
import com.sixkids.designsystem.R as UlbanRes
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.EditFAB
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Post
import com.sixkids.student.board.R
import com.sixkids.student.board.component.PostItem
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.util.formatToMonthDayTimeKorean

private const val TAG = "D107"

@Composable
fun StudentBoardMainRoute(
    viewModel: StudentBoardMainViewModel = hiltViewModel(),
    navigateToStudentBoardDetail: (postId:Long) -> Unit,
    navigateToStudentBoardWrite: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    padding: PaddingValues
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Log.d(TAG, "StudentBoardMainRoute: ")
        viewModel.getPostList()
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                StudentBoardMainEffect.NavigateToPostDetail -> {}
                StudentBoardMainEffect.NavigateToWritePost -> {}
                is StudentBoardMainEffect.OnShowSnackBar -> {
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
        StudentBoardMainScreen(
            postState = uiState,
            postItems = viewModel.postList?.collectAsLazyPagingItems(),
            postItemOnclick = navigateToStudentBoardDetail,
            fabClick = navigateToStudentBoardWrite
        )
    }
}

@Composable
fun StudentBoardMainScreen(
    modifier: Modifier = Modifier,
    postState: StudentBoardMainState = StudentBoardMainState(),
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
            UlbanDefaultAppBar(
                leftIcon = UlbanRes.drawable.board,
                title = stringResource(id = R.string.student_board_main_post),
                content = stringResource(id = R.string.student_board_main_post),
                body = postState.classString.replace("\n", " "),
                color = Blue
            )

            if (postItems == null){
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.student_board_post_no_items),
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
fun StudentBoardMainScreenPreview() {
    StudentBoardMainScreen()
}