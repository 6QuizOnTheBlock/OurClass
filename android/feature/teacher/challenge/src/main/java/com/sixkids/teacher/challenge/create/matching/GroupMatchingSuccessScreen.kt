package com.sixkids.teacher.challenge.create.matching

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.item.StudentSimpleCardItem
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.ChallengeGroup
import com.sixkids.model.MemberSimple
import com.sixkids.teacher.challenge.R

@Composable
fun GroupMatchingSuccessRoute() {

}

@Composable
fun GroupMatchingSuccessScreen(
    modifier: Modifier = Modifier,
    groupMatchingSuccessState: GroupMatchingSuccessState = GroupMatchingSuccessState(),
    onNextButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.matching_success),
            style = UlbanTypography.titleSmall
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            groupMatchingSuccessState.groupList.forEachIndexed { index, group ->
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "그룹 ${index + 1}",
                    style = UlbanTypography.bodyMedium
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    color = Blue,
                    thickness = 2.dp
                )
                // 수동 그리드 레이아웃
                Column(modifier = Modifier.fillMaxWidth()) {
                    val rows = group.memberList.chunked(4) // 4개의 아이템씩 묶음
                    rows.forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            rowItems.forEach { member ->
                                StudentSimpleCardItem(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp),
                                    name = member.name,
                                    photo = member.photo
                                )
                            }
                            // 빈 공간 채우기 (열이 4개 미만일 때)
                            repeat(4 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp)) // 행 간 간격
                    }
                }
            }
        }
        UlbanFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.next),
            onClick = onNextButtonClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMatchingSuccessScreenPreview() {
    GroupMatchingSuccessScreen(
        groupMatchingSuccessState = GroupMatchingSuccessState(
            groupList = listOf(
                ChallengeGroup(
                    headCount = 2,
                    memberList = listOf(
                        MemberSimple(
                            name = "김철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "김영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "김철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "김영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "김영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                    )
                ),
                ChallengeGroup(
                    headCount = 2,
                    memberList = listOf(
                        MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ), MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        )
                    )
                ),
                ChallengeGroup(
                    headCount = 2,
                    memberList = listOf(
                        MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ), MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        )
                    )
                ),
                ChallengeGroup(
                    headCount = 2,
                    memberList = listOf(
                        MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ), MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        )
                    )
                ),
                ChallengeGroup(
                    headCount = 2,
                    memberList = listOf(
                        MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ), MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        )
                    )
                ),
                ChallengeGroup(
                    headCount = 2,
                    memberList = listOf(
                        MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ), MemberSimple(
                            name = "박철수",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        MemberSimple(
                            name = "박영희",
                            photo = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        )
                    )
                )
            )
        )
    )
}