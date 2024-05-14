package com.sixkids.teacher.manageclass.chattingfilter.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.R as UlbanRes


@Composable
fun ChattingFilterItem(
    modifier: Modifier = Modifier,
    text: String = "",
    deleteOnclick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = UlbanTypography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.clickable { deleteOnclick() },
                imageVector = ImageVector.vectorResource(id = UlbanRes.drawable.ic_delete),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChattingFilterItemPreview() {
    ChattingFilterItem(
        text = "필터 단어"
    )
}