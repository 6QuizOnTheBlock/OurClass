package com.sixkids.student.board.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun CommentCount(
    count: Int
) {
    Row {
        Icon(
            imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_chat_bubble),
            contentDescription = null
        )
        Text(
            text = count.toString(),
            style = UlbanTypography.bodyMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CommentCountPreview() {
    CommentCount(10)
}