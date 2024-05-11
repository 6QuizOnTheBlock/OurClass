package com.sixkids.designsystem.component.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.util.formatToMonthDayTime
import java.time.LocalDateTime

@Composable
fun UlbanRelayItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 16.dp
    ),
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    userCount: Int,
    color: Color = Cream,
    lastMemberName: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = CardDefaults.outlinedShape,
    ) {
        Column(
            modifier = modifier.padding(
                padding
            )
        ) {
            Text(
                text = stringResource(R.string.relay),
                style = UlbanTypography.titleSmall
            )
            Spacer(modifier = modifier.padding(8.dp))

            Text(
                text = "${startDate.formatToMonthDayTime()} ~ ${endDate.formatToMonthDayTime()}",
                style = UlbanTypography.bodySmall
            )
            Spacer(modifier = modifier.padding(8.dp))

            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                AsyncImage(
                    model = R.drawable.bomb,
                    placeholder = painterResource(id =R.drawable.bomb),
                    modifier = Modifier.size(42.dp),
                    contentDescription = "bomb")

                Spacer(modifier = modifier.padding(4.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.relay_item_count, userCount),
                        style = UlbanTypography.bodySmall
                    )
                    Spacer(modifier = modifier.padding(2.dp))
                    Text(
                        text = stringResource(R.string.relay_item_last_member, lastMemberName),
                        style = UlbanTypography.bodyMedium
                    )
                }
            }

        }
    }

}

@Composable
@Preview(showBackground = true)
fun UlbanChallengeItemPreview() {
    UlbanRelayItem(
        startDate = LocalDateTime.now(),
        endDate = LocalDateTime.now(),
        userCount = 0,
        lastMemberName = "홍유준"
    )
}
