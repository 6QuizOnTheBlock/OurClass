package com.sixkids.teacher.board.postdetail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Comment
import com.sixkids.model.MemberSimple
import com.sixkids.model.PostDetail
import com.sixkids.model.Recomment
import com.sixkids.teacher.board.postdetail.component.CommentItem
import com.sixkids.teacher.board.postdetail.component.PostWriterInfo
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun PostDetailRoute(
    padding: PaddingValues
) {
    Box(modifier = Modifier.padding(padding)) {
        PostDetailScreen(
            //TODO : 데이터 통신 로직 구현 이후 더미데이터 제거
            postDetailState = postDetailStateDummy
        )
    }
}

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    postDetailState: PostDetailState,
    postDeleteOnclick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(ScrollState(0)),
    ) {
        // 작성자 정보
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PostWriterInfo(
                height = 60.dp,
                writer = postDetailState.postDetail.writeMember.name,
                dateString = postDetailState.postDetail.createTime,
                writerImageUrl = postDetailState.postDetail.writeMember.photo
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { postDeleteOnclick() },
                imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_delete),
                contentDescription = "더보기"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = postDetailState.postDetail.title,
            style = UlbanTypography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = postDetailState.postDetail.content,
            style = UlbanTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Black
        )
        // 댓글 목록
        for (comment in postDetailState.postDetail.comments) {
            CommentItem(
                writer = comment.member.name,
                dateString = comment.createTime,
                writerImageUrl = comment.member.photo,
                commentString = comment.content
            )
            for (recomment in comment.recomments) {
                Row {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_recomment),
                        contentDescription = null
                    )
                    CommentItem(
                        writer = recomment.member.name,
                        dateString = recomment.createTime,
                        writerImageUrl = recomment.member.photo,
                        commentString = recomment.content,
                        isRecomment = true
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    PostDetailScreen(
        postDetailState = postDetailStateDummy
    )
}


val recommentDummy = Recomment(
    1,
    member = MemberSimple(
        id = 1,
        name = "작성자",
        photo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKe3bkhl96AgtmHyTiKW-KXRst2-5MoY6xB9-mZP74BQ&s"
    ),
    content = "내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용",
    createTime = "01/01 00:00",
    updateTime = "01/01 00:00",
    1,
)

val commentDummy = Comment(
    1,
    member = MemberSimple(
        id = 1,
        name = "작성자",
        photo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKe3bkhl96AgtmHyTiKW-KXRst2-5MoY6xB9-mZP74BQ&s"
    ),
    "내용내용내용 내용내용내용내용내용내용내용내용내용 내용내용내용내용내용내용내용내용내용",
    "01/01 00:00",
    "01/01 00:00",
    listOf(recommentDummy, recommentDummy)
)

val postDetailStateDummy = PostDetailState(
    postDetail = PostDetail(
        title = "제목",
        content = "내용내용내용내용내용내용내용내용내용내용내용내용내용",
        writeMember = MemberSimple(
            id = 1,
            name = "작성자",
            photo = "https://picsum.photos/200/300"
        ),
        createTime = "01/01 00:00",
        ImageUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKe3bkhl96AgtmHyTiKW-KXRst2-5MoY6xB9-mZP74BQ&s",
        comments = listOf(commentDummy, commentDummy)
    )
)