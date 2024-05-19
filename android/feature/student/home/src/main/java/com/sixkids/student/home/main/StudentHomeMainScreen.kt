package com.sixkids.student.home.main

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.item.StudentSimpleCardItem
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueText
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.OrangeText
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.PurpleText
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.YellowText
import com.sixkids.designsystem.theme.component.card.ContentVerticalCard
import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.student.home.R
import com.sixkids.student.home.main.component.GreetingDialog
import com.sixkids.student.home.main.component.StudentMainInfo
import com.sixkids.ui.SnackbarToken
import com.sixkids.designsystem.R as UlbanRes

private const val TAG = "D107"
@Composable
fun StudentHomeMainRoute(
    viewModel: StudentHomeMainViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToAnnounce: () -> Unit,
    navigateToTagHello: () -> Unit,
    navigateToRank: () -> Unit,
    navigateToChatting: () -> Unit,
    navigateToGreetingSender: () -> Unit,
    navigateToGreetingReceiver: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getStudentHomeInfo()
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                StudentHomeMainEffect.navigateToAnnounce -> navigateToAnnounce()
                StudentHomeMainEffect.navigateToChatting -> navigateToChatting()
                StudentHomeMainEffect.navigateToRank -> navigateToRank()
                StudentHomeMainEffect.navigateToTagHello -> navigateToTagHello()
                is StudentHomeMainEffect.onShowSnackBar -> onShowSnackBar(SnackbarToken(message = sideEffect.message))
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        StudentHomeMainScreen(
            studentHomeMainState = uiState,
            announceCardOnClick = navigateToAnnounce,
            chattingCardOnClick = navigateToChatting,
            rankCardOnClick = navigateToRank,
            showGreetingDialog =  viewModel::showGreetingDialog,
        )
        if (uiState.isShowGreetingDialog) {
            BackHandler(enabled = true) {
                Log.d(TAG, "BackHandler triggered")
                viewModel.offDialog()
            }

            GreetingDialog(
                senderClick = navigateToGreetingSender,
                receiverClick = navigateToGreetingReceiver,
                cancelClick = viewModel::offDialog
            )
        }
    }
}

@Composable
fun StudentHomeMainScreen(
    modifier: Modifier = Modifier,
    studentHomeMainState: StudentHomeMainState = StudentHomeMainState(),
    announceCardOnClick: () -> Unit = {},
    chattingCardOnClick: () -> Unit = {},
    rankCardOnClick: () -> Unit = {},
    showGreetingDialog: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        StudentMainInfo(
            name = studentHomeMainState.studentName,
            imageUrlString = studentHomeMainState.studentImageUrl,
            classString = studentHomeMainState.studentClass,
            exp = studentHomeMainState.studentExp
        )
        Spacer(modifier = Modifier.weight(1f))
        Row {
            ContentVerticalCard(
                cardModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(end = 10.dp),
                cardColor = Orange,
                textColor = OrangeText,
                imageDrawable = UlbanRes.drawable.announce,
                text = stringResource(id = R.string.student_home_main_announce),
                onClick = announceCardOnClick
            )
            ContentVerticalCard(
                cardModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(start = 10.dp),
                cardColor = Blue,
                textColor = BlueText,
                imageDrawable = UlbanRes.drawable.tag_hello,
                text = stringResource(id = R.string.student_home_main_hi),
                onClick = showGreetingDialog
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.student_home_main_best_friends),
            style = UlbanTypography.titleSmall,
            modifier = Modifier.padding(start = 10.dp)
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
                textColor = PurpleText,
                imageDrawable = UlbanRes.drawable.chat,
                text = stringResource(id = R.string.student_home_main_chatting),
                onClick = chattingCardOnClick
            )
            ContentVerticalCard(
                cardModifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(start = 10.dp),
                cardColor = Yellow,
                textColor = YellowText,
                imageDrawable = UlbanRes.drawable.rank,
                text = stringResource(id = R.string.student_home_main_rank),
                onClick = rankCardOnClick
            )
        }
        Spacer(modifier = Modifier.weight(1f))
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