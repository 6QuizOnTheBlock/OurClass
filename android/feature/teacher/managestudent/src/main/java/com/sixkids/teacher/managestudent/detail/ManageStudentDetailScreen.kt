package com.sixkids.teacher.managestudent.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sixkids.designsystem.component.item.StudentSimpleCardItem
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.designsystem.R as DesignR

@Composable
fun ManageStudentDetailRoute(
    viewModel: ManageStudentDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }


    ManageStudentDetailScreen(
        uiState = uiState
    )
}

@Composable
fun ManageStudentDetailScreen(
    uiState: ManageStudentDetailState = ManageStudentDetailState(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.padding(top = 20.dp)) {
            UserInfoSection(
                photo = uiState.memberDetail.photo,
                name = uiState.memberDetail.name,
                exp = uiState.memberDetail.exp
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "소외도 ${uiState.memberDetail.isolationPoint}점",
                style = UlbanTypography.titleSmall,
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "${uiState.memberDetail.name} 학생과 가장 친한 친구들",
                style = UlbanTypography.bodyMedium
            )
            Spacer(modifier = Modifier.size(5.dp))

            BestFriendsSection(
                uiState.studentList,
                onFriendClick = { /*TODO*/ }
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(text = "활동 내역", style = UlbanTypography.titleSmall,
                modifier = Modifier.padding(start = 10.dp))

            Spacer(modifier = Modifier.size(5.dp))

            StatisticsSection(
                challenge = uiState.memberDetail.challengeCount,
                relay = uiState.memberDetail.relayCount,
                post = uiState.memberDetail.postCount
            )
        }
    }
}

@Composable
fun UserInfoSection(
    photo: String,
    name: String,
    exp: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = photo, contentDescription = "profile",
            modifier = Modifier
                .size(68.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(20.dp))

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(text = name, style = UlbanTypography.titleMedium)
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "$exp exp", style = UlbanTypography.titleSmall)
        }
    }
}

@Composable
fun BestFriendsSection(
    bestFriends: List<MemberSimpleWithScore>,
    onFriendClick: (Long) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        bestFriends.forEach {
            StudentSimpleCardItem(
                modifier = Modifier.weight(1f),
                name = it.memberSimple.name,
                photo = it.memberSimple.photo,
                score = it.relationPoint,
                onClick = onFriendClick
            )
        }

    }
}

@Composable
fun StatisticsSection(
    challenge: Int,
    relay: Int,
    post: Int
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticsCard(
                Red,
                image = DesignR.drawable.hifive,
                title = "함께 달리기",
                count = "${challenge}회 참여"
            )
            StatisticsCard(
                Orange,
                image = DesignR.drawable.relay,
                title = "이어 달리기",
                count = "${relay}회 참여"
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticsCard(
                Blue,
                image = DesignR.drawable.board,
                title = "게시글",
                count = "${post}개 작성"
            )


            val screenWidthDp = with(LocalDensity.current) {
                LocalContext.current.resources.displayMetrics.widthPixels.toDp()
            }

            Box(
                modifier = Modifier
                    .size(screenWidthDp / 3 + screenWidthDp / 10)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Purple)

            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(10.dp),
                ) {

                    Text(
                        text = "내보내기",
                        style = UlbanTypography.titleSmall,
                        modifier = Modifier.padding(vertical = 10.dp).padding(top = 10.dp, start = 15.dp)
                    )

                    AsyncImage(
                        model = DesignR.drawable.quit,
                        placeholder = painterResource(id = DesignR.drawable.quit),
                        contentDescription = "img",
                        modifier = Modifier.size(200.dp).align(Alignment.End).offset(x = 40.dp, y = -10.dp)
                    )

                }
            }
        }
    }

}

@Composable
fun StatisticsCard(
    color: Color = Red,
    @DrawableRes image: Int,
    title: String,
    count: String
) {
    val screenWidthDp = with(LocalDensity.current) {
        LocalContext.current.resources.displayMetrics.widthPixels.toDp()
    }

    Box(
        modifier = Modifier
            .size(screenWidthDp / 3 + screenWidthDp / 10)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(bottom = 10.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = image,
                contentDescription = "img",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = title,
                style = UlbanTypography.titleSmall,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Text(text = count, style = UlbanTypography.titleSmall)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ManageStudentDetailScreenPreview() {
    UlbanTheme {
        ManageStudentDetailScreen()
    }
}