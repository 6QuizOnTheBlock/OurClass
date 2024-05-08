package com.sixkids.teacher.board.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.EditFAB
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.model.Post
import com.sixkids.teacher.board.R
import com.sixkids.teacher.board.post.component.PostItem
import com.sixkids.ui.util.formatToMonthDayTime
import com.sixkids.ui.util.formatToMonthDayTimeKorean
import java.time.LocalDateTime
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun PostRoute(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        PostScreen()
    }
}


@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    postState: PostState = PostState(),
    fabClick: () -> Unit = {}
) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                items(postState.postList.size) {
                    val item = postState.postList[it]
                    PostItem(
                        title = item.title,
                        writer = item.writer,
                        commentCount = item.commentCount,
                        dateString = item.time.formatToMonthDayTime()
                    )
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
    }
}


@Preview(showBackground = true)
@Composable
fun PostRoutePreview() {
    PostScreen(
        postState = PostState(
            postList = listOf(
                Post(
                    id = 0,
                    title = "이따 마크 할 사람~~!이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                ),
                Post(
                    id = 1,
                    title = "이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                ),
                Post(
                    id = 2,
                    title = "이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                ),
                Post(
                    id = 3,
                    title = "이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                ),
                Post(
                    id = 4,
                    title = "이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                ),
                Post(
                    id = 5,
                    title = "이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                ),
                Post(
                    id = 6,
                    title = "이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                ),
                Post(
                    id = 7,
                    title = "이따 마크 할 사람~~!",
                    writer = "오하빈",
                    time = LocalDateTime.now(),
                    commentCount = 3
                )
            )
        )
    )
}