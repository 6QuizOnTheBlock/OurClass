package com.sixkids.student.home.announce.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun PageTitle(
    modifier: Modifier = Modifier,
    title: String,
    cancelOnclick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable { cancelOnclick() },
            imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_cancel_post),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            style = UlbanTypography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PageTitlePreview() {
    PageTitle(
        title = "글 쓰기",
        cancelOnclick = {}
    )
}