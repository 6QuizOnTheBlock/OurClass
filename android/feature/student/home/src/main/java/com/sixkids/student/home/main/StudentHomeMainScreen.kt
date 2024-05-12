package com.sixkids.student.home.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.item.StudentSimpleCardItem
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.component.card.ContentVerticalCard
import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.student.home.R
import com.sixkids.student.home.main.component.StudentMainInfo
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun StudentHomeMainRoute(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier.padding(padding)
    ) {
        StudentHomeMainScreen()
    }
}

@Composable
fun StudentHomeMainScreen(
    modifier: Modifier = Modifier,
    studentHomeMainState: StudentHomeMainState = StudentHomeMainState()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        StudentMainInfo(
            name = studentHomeMainState.studentName,
            imageUrlString = studentHomeMainState.studentImageUrl,
            classString = studentHomeMainState.studentClass,
            exp = studentHomeMainState.studentExp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            ContentVerticalCard(
                cardModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(end = 10.dp),
                cardColor = Orange,
                imageDrawable = UlbanRes.drawable.announce,
                text = stringResource(id = R.string.student_home_main_announce)
            )
            ContentVerticalCard(
                cardModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(start = 10.dp),
                cardColor = Blue,
                imageDrawable = UlbanRes.drawable.tag_hello,
                text = stringResource(id = R.string.student_home_main_hi)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.student_home_main_best_friends),
            style = UlbanTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            studentHomeMainState.bestFriendList.take(3).forEach {
                Spacer(modifier = Modifier.width(10.dp))
                StudentSimpleCardItem(
                    modifier = modifier
                        .height(130.dp)
                        .width(100.dp),
                    name = it.memberSimple.name,
                    photo = it.memberSimple.photo,
                    score = it.relationPoint
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            ContentVerticalCard(
                cardModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(end = 10.dp),
                cardColor = Purple,
                imageDrawable = UlbanRes.drawable.chat,
                text = stringResource(id = R.string.student_home_main_chatting)
            )
            ContentVerticalCard(
                cardModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(start = 10.dp),
                cardColor = Yellow,
                imageDrawable = UlbanRes.drawable.rank,
                text = stringResource(id = R.string.student_home_main_rank)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentHomeMainScreenPreview() {
    StudentHomeMainScreen(
        studentHomeMainState = StudentHomeMainState(
            studentName = "홍유준",
            studentImageUrl = "https://www.google.com",
            studentClass = "구미초등학교 1학년 1반",
            studentExp = 2340,
            bestFriendList = listOf(
                MemberSimpleWithScore(
                    memberSimple = MemberSimple(
                        name = "김철수",
                        photo = "https://www.google.com"
                    ),
                    relationPoint = 234
                ),
                MemberSimpleWithScore(
                    memberSimple = MemberSimple(
                        name = "김영희",
                        photo = "https://www.google.com"
                    ),
                    relationPoint = 234
                ),
                MemberSimpleWithScore(
                    memberSimple = MemberSimple(
                        name = "박영수",
                        photo = "https://www.google.com"
                    ),
                    relationPoint = 234
                )
            )
        )
    )
}