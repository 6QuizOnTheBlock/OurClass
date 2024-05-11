package com.sixkids.student.group.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.challenge.R

@Composable
fun GroupWaiting(
    memberList: List<MemberIconItem> = emptyList(),
    onDoneClick: () -> Unit = {},
    onRemoveClick: (Long) -> Unit = {},
    donButtonEnable: Boolean = false
) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cream
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "2명의 친구들을 더 모아보세요",
                style = UlbanTypography.bodyMedium
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(memberList) { item ->
                    MemberIcon(
                        memberId = item.memberId,
                        name = item.name,
                        photo = item.photo,
                        showX = item.showX,
                        isActive = item.isActive,
                        onRemoveClick = onRemoveClick
                    )
                }
            }
            UlbanFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.done),
                onClick = onDoneClick,
                enabled = donButtonEnable
            )
        }
    }
}

data class MemberIconItem(
    val memberId: Long,
    val name: String,
    val photo: String,
    val showX: Boolean = false,
    val isActive: Boolean = false
)


@Preview(showBackground = true)
@Composable
fun GroupWaitingPreview() {
    GroupWaiting(
        memberList = List(4){
            MemberIconItem(
                memberId = it.toLong(),
                name = "name$it",
                photo = "",
                showX = false,
                isActive = it % 2 == 0
            )
        }
    )
}
