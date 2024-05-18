package com.sixkids.designsystem.component.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Gray
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.ui.util.formatToMonthDayTime
import java.time.LocalDateTime

@Composable
fun UlbanRunnerItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 20.dp
    ),
    time: LocalDateTime,
    memberName: String,
    memberPhoto: String,
    question: String,
    isLastTurn: Boolean = false,
    color: Color = Cream
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.wrapContentSize().padding(bottom = 10.dp)) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = color
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = CardDefaults.outlinedShape,
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(padding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = memberPhoto,
                        placeholder = painterResource(id = R.drawable.student_boy),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = memberName,
                            style = UlbanTypography.titleSmall
                        )
                        Text(
                            text = time.formatToMonthDayTime(),
                            style = UlbanTypography.bodySmall
                        )
                        Text(
//                        text = stringResource(R.string.runner_question),
                            text = "받은 질문",
                            style = UlbanTypography.bodySmall.copy(color = Gray)
                        )
                        Text(
                            text = question,
                            style = UlbanTypography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            if (isLastTurn) {
                AsyncImage(
                    model = R.drawable.bomb,
                    placeholder = painterResource(id = R.drawable.bomb),
                    contentDescription = "bomb",
                    modifier = Modifier
                        .size(72.dp)
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }
        }
        if (!isLastTurn) {
            Image(
                painter = painterResource(id = R.drawable.ic_down_arrow),
                contentDescription = "next",
                modifier = Modifier
                    .padding(10.dp)
                    .size(32.dp)
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun UlbanRunnerItemPreview() {
    UlbanRunnerItem(
        time = LocalDateTime.now(),
        memberName = "홍유준",
        memberPhoto = "https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/c5cef8d2-f085-45ca-b359-f76e39f2bca3_profile.jpg",
        question = "이번 턴은 어떤"
    )
}