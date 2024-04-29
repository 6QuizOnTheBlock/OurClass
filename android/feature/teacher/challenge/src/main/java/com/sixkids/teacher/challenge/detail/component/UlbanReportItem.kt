package com.sixkids.teacher.challenge.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.Cream
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.RedDark
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.Group
import com.sixkids.model.MemberSimple
import com.sixkids.model.Report
import com.sixkids.ui.util.formatToMonthDayTime
import java.time.LocalDateTime

@Composable
fun UlbanReportItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(16.dp),
    report: Report,
    onReject: () -> Unit = {},
    onAccept: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Cream
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
            Spacer(modifier = modifier.padding(4.dp))
            Row {
                Text(
                    text = report.startDate.formatToMonthDayTime(),
                    style = UlbanTypography.bodySmall
                )
                Text(
                    text = " ~ ",
                    style = UlbanTypography.bodySmall
                )
                Text(
                    text = report.endDate.formatToMonthDayTime(),
                    style = UlbanTypography.bodySmall
                )
            }
            AsyncImage(
                model = report.file,
                contentDescription = "과제 사진",
                modifier = modifier
                    .height(240.dp)
                    .padding(vertical = 8.dp)
            )
            MemberSimpleList(
                modifier = modifier.padding(vertical = 16.dp),
                memberList = report.group.studentList,
                leaderId = report.group.leaderId
            )
            Text(text = report.content, style = UlbanTypography.bodySmall)
            if (report.accepted.not()) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Button(
                        modifier = modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        onClick = { onReject() }) {
                        Text(text = "X", style = UlbanTypography.bodyLarge, color = RedDark)
                    }
                    Spacer(modifier = modifier.width(8.dp))
                    Button(
                        modifier = modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue),
                        onClick = { onAccept() }) {
                        Text(text = "O", style = UlbanTypography.bodyLarge, color = BlueDark)
                    }
                }
            }
        }

    }
}

@Composable
fun MemberSimpleList(
    modifier: Modifier = Modifier,
    memberList: List<MemberSimple> = emptyList(),
    leaderId: Long = 0L
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(memberList) { member ->
            MemberSimpleItem(
                member = member,
                isLeader = member.id == leaderId
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UlbanReportItemPreview() {

    UlbanTheme {
        UlbanReportItem(
            report = Report(
                content = "4명 다 모여서 쿵푸팬더 4 다같이 봤어요!!",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now(),
                accepted = false,
                file = "https://file2.nocutnews.co.kr/newsroom/image/2024/04/05/202404052218304873_0.jpg",
                group = Group(
                    leaderId = 1,
                    studentList = listOf(
                        MemberSimple(
                            id = 1,
                            name = "김규리",
                            photo = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSGfpQ3m-QWiXgCBJJbrcUFdNdWAhj7rcUqjeNUC6eKcXZDAtWm"
                        ),
                        MemberSimple(
                            id = 2,
                            name = "오하빈",
                            photo = "https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg"
                        ),
                        MemberSimple(
                            id = 3,
                            name = "차성원",
                            photo = "https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/E172/production/_126241775_getty_cats.png"
                        ),
                        MemberSimple(
                            id = 4,
                            name = "정철주",
                            photo = "https://image.newsis.com/2023/07/12/NISI20230712_0001313626_web.jpg?rnd=20230712163021"
                        )
                    )
                )
            )
        )
    }
}
