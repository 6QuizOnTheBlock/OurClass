package com.sixkids.student.home.announce.announcedetail

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
import com.sixkids.student.home.announce.component.CommentItem
import com.sixkids.student.home.announce.component.CommentTextField
import com.sixkids.student.home.announce.component.PostWriterInfo
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.util.formatToMonthDayTime

@Composable
fun StudentAnnounceDetailRoute(
    viewModel: StudentAnnounceDetailViewModel = hiltViewModel(),
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
                StudentAnnounceDetailEffect.RefreshAnnounceDetail -> {
                    keyboardHideState = true
                    viewModel.getAnnounceDetail()
                }

                is StudentAnnounceDetailEffect.OnShowSnackbar -> {
                    onShowSnackBar(SnackbarToken(message = sideEffect.message))
                }
            }
        }
    }

    Box(modifier = Modifier.padding(padding)) {
        StudentAnnounceDetailScreen(
            studentAnnounceDetailState = uiState,
            onCommentTextChanged = viewModel::onCommentTextChanged,
            onClickComment = viewModel::onSelectedCommentId,
            onClickSubmitComment = viewModel::onNewComment,
        )
    }
}

@Composable
fun StudentAnnounceDetailScreen(
    modifier: Modifier = Modifier,
    studentAnnounceDetailState: StudentAnnounceDetailState = StudentAnnounceDetailState(),
    onCommentTextChanged: (String) -> Unit = {},
    onClickComment: (Long) -> Unit = {},
    onClickSubmitComment: () -> Unit = {},
) {

    val scrollState = rememberScrollState()

    BackHandler(
        enabled = studentAnnounceDetailState.selectedCommentId != null,
        onBack = { onClickComment(studentAnnounceDetailState.selectedCommentId ?: 0) }
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
                        writer = studentAnnounceDetailState.postDetail.writeMember.name,
                        dateString = studentAnnounceDetailState.postDetail.createTime.formatToMonthDayTime(),
                        writerImageUrl = studentAnnounceDetailState.postDetail.writeMember.photo
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = studentAnnounceDetailState.postDetail.title,
                    style = UlbanTypography.titleLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                // 이미지
                if (studentAnnounceDetailState.postDetail.imageUri.isNotEmpty()) {
                    AsyncImage(
                        model = studentAnnounceDetailState.postDetail.imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = studentAnnounceDetailState.postDetail.content,
                    style = UlbanTypography.bodyLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Black
                )
                // 댓글 목록
                for (comment in studentAnnounceDetailState.postDetail.comments) {
                    CommentItem(
                        selected = studentAnnounceDetailState.selectedCommentId == comment.id,
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
                msg = studentAnnounceDetailState.commentText,
                onTextIuputChange = onCommentTextChanged,
                onSendClick = { onClickSubmitComment() }
            )
        }


        if (studentAnnounceDetailState.isLoading) {
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentAnnounceDetailScreenPreview() {
    StudentAnnounceDetailScreen()
}