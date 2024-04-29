package com.sixkids.teacher.challenge.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimple


@Composable
fun MemberSimpleItem(
    modifier: Modifier = Modifier,
    member: MemberSimple,
    isLeader: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = member.photo,
            contentScale = ContentScale.Crop,
            contentDescription = "프로필 사진"
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLeader) {
                Image(
                    painter = painterResource(id = R.drawable.crown),
                    contentDescription = "리더 아이콘",
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(text = member.name, style = UlbanTypography.bodySmall)
        }
    }
}
