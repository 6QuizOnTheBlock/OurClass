package com.sixkids.teacher.challenge.create.matching.component

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
    memberIconItem: MemberIconItem,
    onIconClick: (MemberIconItem) -> Unit = {},
    onRemoveClick: (Long) -> Unit = {},
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .background(
                if (memberIconItem.isActive) Color.Transparent else Color.Gray,
                shape = RoundedCornerShape(8.dp),
            )
            .graphicsLayer {
                alpha = if (memberIconItem.isActive) 1f else 0.5f
            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),

        onClick = { onIconClick(memberIconItem) }
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
                        model = memberIconItem.member.photo,
                        contentDescription = null,
                        modifier = modifier.size(48.dp)
                    )
                    if (memberIconItem.showX) {
                        Icon(
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.ic_close_filled),
                            contentDescription = "Close icon",
                            tint = Color.Red,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                                .clickable {
                                    onRemoveClick(memberIconItem.member.id)
                                }
                        )
                    }
                }
                Text(
                    text = memberIconItem.member.name,
                    style = UlbanTypography.bodyMedium,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }


}

data class MemberIconItem(
    val member: MemberSimple = MemberSimple(),
    val showX: Boolean = false,
    val isActive: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun MemberIconPreview() {
    MemberIcon(
        memberIconItem = MemberIconItem(
            member = MemberSimple(
                id = 1,
                name = "Leader",
                photo = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"

            ),
            showX = true,
            isActive = true
        ),
        onIconClick = {},
        onRemoveClick = {}
    )
}
