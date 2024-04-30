package com.sixkids.designsystem.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.util.formatToMonthDayTime
import java.time.LocalDateTime

@Composable
fun UlbanChallengeItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 16.dp
    ),
    title: String,
    description: String,
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    userCount: Int,
    color: Color = Cream
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = CardDefaults.outlinedShape
    ) {
        Column(
            modifier = modifier.padding(
                padding
            )
        ) {
            Text(
                text = title,
                style = UlbanTypography.titleSmall
            )
            Spacer(modifier = modifier.padding(8.dp))
            Text(
                text = description,
                style = UlbanTypography.bodySmall
            )
            Spacer(modifier = modifier.padding(8.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.member),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(text = stringResource(id = R.string.hifive_user_count, userCount))
            }
            Spacer(modifier = modifier.padding(4.dp))
            Row {
                Text(
                    text = startDate.formatToMonthDayTime(),
                    style = UlbanTypography.bodySmall
                )
                Text(
                    text = " ~ ",
                    style = UlbanTypography.bodySmall
                )
                Text(
                    text = endDate.formatToMonthDayTime(),
                    style = UlbanTypography.bodySmall
                )
            }

        }

    }
}
