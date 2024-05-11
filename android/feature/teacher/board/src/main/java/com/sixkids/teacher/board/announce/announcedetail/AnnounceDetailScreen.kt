package com.sixkids.teacher.board.announce.announcedetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sixkids.designsystem.R
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimple
import com.sixkids.model.PostDetail
import com.sixkids.teacher.board.post.postdetail.commentDummy
import com.sixkids.teacher.board.post.postdetail.component.CommentItem
import com.sixkids.teacher.board.post.postdetail.component.CommentTextField
import com.sixkids.teacher.board.post.postdetail.component.PostWriterInfo
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.util.formatToMonthDayTime
import java.time.LocalDateTime

@Composable
fun AnnounceDetailRoute(
    viewModel: AnnounceDetailViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    // 키보드 숨기기
    var keyboardHideState by remember { mutableStateOf(false) }
    if (keyboardHideState) {
        LocalSoftwareKeyboardController.current?.hide()
        keyboardHideState = false
    }

    LaunchedEffect(Unit) {
        viewModel.getAnnounceDetail()
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                AnnounceDetailEffect.RefreshAnnounceDetail -> {
                    keyboardHideState = true
                    viewModel.getAnnounceDetail()
                }

                is AnnounceDetailEffect.OnShowSnackbar -> {
                    onShowSnackBar(SnackbarToken(message = sideEffect.message))
                }
            }
        }
    }

    Box(modifier = Modifier.padding(padding)) {
        AnnounceDetailScreen(
            announceDetailState = uiState,
            onCommentTextChanged = viewModel::onCommentTextChanged,
            onClickComment = viewModel::onSelectedCommentId,
            onClickSubmitComment = viewModel::onNewComment,
        )
    }
}

@Composable
fun AnnounceDetailScreen(
    modifier: Modifier = Modifier,
    announceDetailState: AnnounceDetailState,
    onCommentTextChanged: (String) -> Unit = {},
    onClickComment: (Long) -> Unit = {},
    onClickSubmitComment: () -> Unit = {},
    postDeleteOnclick: () -> Unit = {}
) {

    val scrollState = rememberScrollState()

    BackHandler(
        enabled = announceDetailState.selectedCommentId != null,
        onBack = { onClickComment(announceDetailState.selectedCommentId ?: 0) }
    )

    Box {
        Column {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(20.dp)
                    .verticalScroll(scrollState),
            ) {
                // 작성자 정보
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PostWriterInfo(
                        height = 60.dp,
                        writer = announceDetailState.postDetail.writeMember.name,
                        dateString = announceDetailState.postDetail.createTime.formatToMonthDayTime(),
                        writerImageUrl = announceDetailState.postDetail.writeMember.photo
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { postDeleteOnclick() },
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                        contentDescription = "더보기"
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = announceDetailState.postDetail.title,
                    style = UlbanTypography.titleLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                // 이미지
                if (announceDetailState.postDetail.imageUri.isNotEmpty()) {
                    AsyncImage(
                        model = announceDetailState.postDetail.imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = announceDetailState.postDetail.content,
                    style = UlbanTypography.bodyLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Black
                )
                // 댓글 목록
                for (comment in announceDetailState.postDetail.comments) {
                    CommentItem(
                        selected = announceDetailState.selectedCommentId == comment.id,
                        writer = comment.member.name,
                        dateString = comment.createTime.formatToMonthDayTime(),
                        writerImageUrl = comment.member.photo,
                        commentString = comment.content,
                        recommentOnclick = {
                            onClickComment(comment.id)
                        }
                    )
                    // 대댓글 목록
                    for (recomment in comment.recomments) {
                        Row {
                            Icon(
                                modifier = Modifier.padding(4.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_recomment),
                                contentDescription = null
                            )
                            CommentItem(
                                writer = recomment.member.name,
                                dateString = recomment.createTime.formatToMonthDayTime(),
                                writerImageUrl = recomment.member.photo,
                                commentString = recomment.content,
                                isRecomment = true
                            )
                        }

                    }
                }
            }
            CommentTextField(
                msg = announceDetailState.commentText,
                onTextIuputChange = onCommentTextChanged,
                onSendClick = { onClickSubmitComment() }
            )
        }


        if (announceDetailState.isLoading) {
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnnounceDetailScreenPreview() {
    AnnounceDetailScreen(
        announceDetailState = AnnounceDetailState(
            postDetail = PostDetail(
                title = "제목",
                content = "내용내용내용내용내용내용내용내용내용내용내용내용내용",
                writeMember = MemberSimple(
                    id = 1,
                    name = "작성자",
                    photo = "https://picsum.photos/200/300"
                ),
                createTime = LocalDateTime.now(),
                imageUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKe3bkhl96AgtmHyTiKW-KXRst2-5MoY6xB9-mZP74BQ&s",
                comments = listOf(commentDummy, commentDummy)
            )
        )
    )
}

