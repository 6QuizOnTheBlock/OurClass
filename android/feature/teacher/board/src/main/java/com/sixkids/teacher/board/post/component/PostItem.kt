package com.sixkids.teacher.board.post.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun PostItem(
    modifier: Modifier = Modifier ,
    title: String,
    writer: String,
    commentCount: Int,
    dateString: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = UlbanTypography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            if (commentCount > 0){
                CommentCount(count = commentCount)
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = writer,
                style = UlbanTypography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = dateString,
                style = UlbanTypography.bodyMedium
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            thickness = 2.dp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {
    PostItem(
        title = "이따 마크 할 사람~~!",
        writer = "오하빈",
        commentCount = 3,
        dateString = "2024.04.16 14:30"
    )
}