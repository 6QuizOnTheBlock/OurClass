package com.sixkids.teacher.board.post.postdetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.textfield.UlbanBasicTextField
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.GrayLight
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.board.R

@Composable
fun CommentTextField(
    msg: String = "",
    onTextIuputChange: (String) -> Unit = {},
    onSendClick: (String) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = GrayLight
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UlbanBasicTextField(
                text = msg,
                onTextChange = onTextIuputChange,
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .weight(1f)
                    .wrapContentHeight(),
                maxLines = 3,
                textStyle = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                hint = stringResource(id = R.string.board_detail_comment_hint)
            )

            Icon(
                Icons.AutoMirrored.Outlined.Send,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 4.dp)
                    .clickable {
                        onSendClick(msg)
                    }
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun CommentTextFieldPreview() {
    CommentTextField()
}