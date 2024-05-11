package com.sixkids.student.group.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimple
import com.sixkids.designsystem.R as DesignSystemR

@Composable
fun MemberIcon(
    modifier: Modifier = Modifier,
    member: MemberSimple,
    onIconClick: (MemberSimple) -> Unit = {},
    onRemoveClick: (Long) -> Unit = {},
    showX: Boolean = false,
    isActive: Boolean = true
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .background(
                if (isActive) Color.Transparent else Color.Gray,
                shape = RoundedCornerShape(8.dp),
            )
            .graphicsLayer {
                alpha = if (isActive) 1f else 0.5f
            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),

        onClick = { onIconClick(member) }
    ) {
        Box(modifier.wrapContentSize()) {

            Column(
                modifier = modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = modifier.wrapContentSize(),
                ) {
                    AsyncImage(
                        model = member.photo,
                        contentDescription = null,
                        modifier = modifier.size(48.dp)
                    )
                    if (showX) {
                        Icon(
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.ic_close_filled),
                            contentDescription = "Close icon",
                            tint = Color.Red,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                                .clickable {
                                    onRemoveClick(member.id)
                                }
                        )
                    }
                }
                Text(
                    text = member.name,
                    style = UlbanTypography.bodyMedium,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun MemberIconPreview() {
    MemberIcon(
        member = MemberSimple(
            id = 1,
            name = "홍길동",
            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
        ),
        onIconClick = {},
        onRemoveClick = {}
    )
}
