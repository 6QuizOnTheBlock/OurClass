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
import com.sixkids.model.MemberSimple
import com.sixkids.student.challenge.R

@Composable
fun GroupWaiting(
    leader: MemberSimple = MemberSimple(),
    memberList: List<MemberIconItem> = emptyList(),
    onDoneClick: () -> Unit = {},
    onRemoveClick: (Long) -> Unit = {},
    groupSize: Int = 0
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
        val remainingMember = groupSize - memberList.size - 1
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = if(remainingMember != 0) {
                    stringResource(R.string.friend_waiting_message, remainingMember)
                } else {
                    stringResource(R.string.can_create_group)
                },
                style = UlbanTypography.bodyMedium
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if(leader.id != 0L) {
                    item {
                        MemberIcon(
                            member = leader,
                            showX = false,
                            isActive = true,
                            onRemoveClick = {}
                        )
                    }
                }
                items(memberList) { item ->
                    MemberIcon(
                        member = MemberSimple(
                            id = item.memberId,
                            name = item.name,
                            photo = item.photo
                        ),
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
                enabled = remainingMember == 0
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
        leader = MemberSimple(
            id = 1,
            name = "leader",
            photo = ""
        ),
        memberList = List(4) {
            MemberIconItem(
                memberId = it.toLong(),
                name = "name$it",
                photo = "",
                showX = false,
                isActive = it % 2 == 0
            )
        },
        groupSize = 5
    )
}
