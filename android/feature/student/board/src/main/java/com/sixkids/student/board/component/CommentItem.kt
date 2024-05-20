package com.sixkids.student.board.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.GrayLight
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    writer: String = "",
    dateString: String = "00/00 00:00",
    writerImageUrl: String = "",
    commentString: String = "",
    isRecomment: Boolean = false,
    recommentOnclick: () -> Unit = {},
    deleteOnclick: (() -> Unit)? = null
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor =
            if (selected) { Blue}
            else if (isRecomment) {GrayLight}
            else {Color.Transparent}
        ),
    ) {
        Column(
            modifier = modifier
                .padding(start = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(36.dp)
                        .aspectRatio(1f),
                    model = writerImageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "작성자 프로필 사진"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = writer,
                    style = UlbanTypography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                if (!isRecomment) {
                    Icon(
                        modifier = Modifier.clickable{recommentOnclick()},
                        imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_chat_bubble_outline),
                        contentDescription = null
                    )
                }
                if (deleteOnclick != null) {
                    Icon(
                        modifier = Modifier.clickable{deleteOnclick()},
                        imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_delete),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = commentString,
                style = UlbanTypography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dateString,
                style = UlbanTypography.bodySmall.copy(
                    color = Gray
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentItemPreview() {
    Column {
        CommentItem(
            writer = "오하빈",
            dateString = "09/01 12:00",
            writerImageUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
            commentString = "댓글 내용",
            deleteOnclick = {},
            selected = true
        )
        CommentItem(
            writer = "오하빈",
            dateString = "09/01 12:00",
            commentString = "댓글 내용",
            writerImageUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
        )
        CommentItem(
            writer = "오하빈",
            dateString = "09/01 12:00",
            commentString = "댓글 내용",
            writerImageUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
            isRecomment = true,
            deleteOnclick = {}
        )
    }

}