package com.sixkids.teacher.board.post.postdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sixkids.designsystem.theme.UlbanTypography

@Composable
fun PostWriterInfo(
    height: Dp = 60.dp,
    writer: String = "",
    dateString: String = "00/00 00:00",
    writerImageUrl: String = ""
) {
    Row(
        modifier = Modifier.height(height),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            model = writerImageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "프로필 사진"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = writer,
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dateString,
                style = UlbanTypography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostWriterInfoPreview() {
    PostWriterInfo(
        height = 60.dp,
        writer = "홍유준 선생님",
        dateString = "10/10 10:10",
        writerImageUrl = ""
    )
}