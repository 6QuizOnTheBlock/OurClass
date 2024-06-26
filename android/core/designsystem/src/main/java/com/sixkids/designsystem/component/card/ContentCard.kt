package com.sixkids.designsystem.theme.component.card


import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.component.progressbar.StudentProgressBar
import com.sixkids.designsystem.theme.npsFont
import java.text.SimpleDateFormat
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun ContentCardViewPreview() {
    UlbanTheme {
        val runningTogetherState = RunningTogetherState(
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            15,
            5
        )
        val runningRelayState = RunningRelayState(
            "커서 어떤 사람이 되고 싶은가요?",
            "오하빈",
            15,
            5
        )
        Column {
            ContentCard(
                modifier = Modifier.fillMaxWidth(),
                contentAligment = ContentAligment.ImageStart_TextEnd,
                cardColor = Red,
                contentName = "함께 달리기",
                contentImageId = R.drawable.hifive
            )
            Spacer(modifier = Modifier.height(20.dp))
            ContentCard(
                modifier = Modifier.fillMaxWidth(),
                contentAligment = ContentAligment.ImageStart_TextEnd,
                cardColor = Red,
                contentName = "함께 달리기",
                contentImageId = R.drawable.hifive,
                runningState = runningTogetherState
            )
            Spacer(modifier = Modifier.height(20.dp))
            ContentCard(
                modifier = Modifier.fillMaxWidth(),
                contentAligment = ContentAligment.ImageEnd_TextStart,
                cardColor = Cream,
                contentName = "이어 달리기",
                contentImageId = R.drawable.relay
            )
            Spacer(modifier = Modifier.height(20.dp))
            ContentCard(
                modifier = Modifier.fillMaxWidth(),
                contentAligment = ContentAligment.ImageEnd_TextStart,
                cardColor = Cream,
                contentName = "이어 달리기",
                contentImageId = R.drawable.relay,
                runningState = runningRelayState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankCardViewPreview() {
    UlbanTheme {
        ContentVerticalCard(
            cardModifier = Modifier
                .padding(20.dp)
                .aspectRatio(1f),
            imageDrawable = R.drawable.rank,
            text = "랭킹"
        )
    }
}

enum class ContentAligment {
    ImageStart_TextEnd,
    ImageEnd_TextStart
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    contentAligment: ContentAligment,
    cardColor: Color,
    contentName: String,
    textColor: Color = Color.Black,
    @DrawableRes contentImageId: Int,
    runningState: RunningState? = null,
    onclick: () -> Unit = {},
    cardHeight: Dp = 160.dp
) {
    Card(
        modifier = modifier.height(cardHeight),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        shape = RoundedCornerShape(24.dp),
        onClick = onclick
    ) {
        Column {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (contentAligment == ContentAligment.ImageStart_TextEnd) {
                    Image(
                        painter = painterResource(id = contentImageId),
                        contentDescription = null,
                        modifier = imageModifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                    if (runningState == null) {
                        Text(
                            text = contentName,
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight(),
                            textAlign = TextAlign.Center,
                            style = UlbanTypography.titleLarge.copy(
                                fontSize = 26.sp,
                                color = textColor
                            )
                        )
                    } else {
                        RunningText(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            runningState = runningState
                        )
                    }

                } else {
                    if (runningState == null) {
                        Text(
                            text = contentName,
                            modifier = Modifier
//                                .padding(20.dp)
                                .weight(1f)
                                .wrapContentHeight(),
                            textAlign = TextAlign.Center,
                            style = UlbanTypography.titleLarge.copy(
                                fontSize = 26.sp,
                                color = textColor
                            )
                        )
                    } else {
                        RunningText(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            runningState = runningState
                        )
                    }
                    Image(
                        painter = painterResource(id = contentImageId),
                        contentDescription = null,
                        modifier = imageModifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                }
            }
            AnimatedVisibility(visible = runningState != null) {
                StudentProgressBar(
                    modifier = Modifier
                        .padding(20.dp),
                    totalStudentCount = runningState?.totalStudent ?: 0,
                    successStudentCount = runningState?.successStudent ?: 0
                )
            }
        }
    }

}

@Composable
fun RunningText(
    modifier: Modifier = Modifier,
    runningState: RunningState
) {
    val dateformat = SimpleDateFormat("MM월 dd일", Locale.KOREA)
    val endDateformat = SimpleDateFormat("MM월 dd일 HH:mm", Locale.KOREA)

    val boldText = if (runningState is RunningTogetherState) {
        "${dateformat.format(runningState.date)} 함께 달리기"
    } else {
        (runningState as RunningRelayState).question
    }
    val normalText = if (runningState is RunningTogetherState) {
        "${endDateformat.format(runningState.endDate)}에 종료됩니다."
    } else {
        "현재 주자는\n${(runningState as RunningRelayState).nowStudent}입니다."
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = boldText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = npsFont
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = normalText,
            fontSize = 14.sp,
            fontFamily = npsFont
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentVerticalCard(
    cardModifier: Modifier = Modifier,
    cardColor: Color = Yellow,
    textColor: Color = Color.Black,
    imageDrawable: Int = R.drawable.rank,
    text: String = "랭킹",
    onClick: () -> Unit = {}
) {
    Card(
        modifier = cardModifier,
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                painter = painterResource(id = imageDrawable),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = UlbanTypography.titleLarge.copy(
                    color = textColor
                )
            )
        }
    }
}