package com.sixkids.teacher.main.organization

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.sixkids.designsystem.component.screen.LoadingScreen
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.Red
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.model.Organization
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import kotlin.math.absoluteValue

@Composable
fun OrganizationListRoute(
    viewModel: OrganizationViewModel = hiltViewModel(),
    navigateToNewClass: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToHome: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            ClassListEffect.NavigateToNewClass -> navigateToNewClass()
            ClassListEffect.NavigateToProfile -> navigateToProfile()
            ClassListEffect.NavigateToHome -> navigateToHome()
            is ClassListEffect.onShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    OrganizationListScreen(
        uiState = uiState,
        onNewClassClick = {
            viewModel.newOrganizationClick()
        },
        onProfileClick = {
            viewModel.profileClick()
        },
        onClassClick = { classId ->
            viewModel.organizationClick(classId)
        }
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrganizationListScreen(
    uiState: ClassListState = ClassListState(),
    onNewClassClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onClassClick: (Int) -> Unit = {}
) {
    val organizationLists = listOf(
        Organization(1, "구미 초등학교\n1학년 1반", 21),
        Organization(2, "구미 초등학교\n1학년 2반", 22),
        Organization(3, "구미 초등학교\n1학년 3반", 23),
        Organization(4, "구미 초등학교\n1학년 4반", 24),
        Organization(5, "구미 초등학교\n1학년 5반", 25),
        Organization(6, "구미 초등학교\n1학년 6반", 26),
        Organization(7, "구미 초등학교\n1학년 7반", 27),
        Organization(8, "구미 초등학교\n1학년 8반", 28),
        Organization(9, "구미 초등학교\n1학년 9반", 29),
        Organization(10, "구미 초등학교\n1학년 10반", 30),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val pagerState = rememberPagerState(pageCount = { organizationLists.size })
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "profile",
                modifier = Modifier
                    .padding(24.dp)
                    .size(40.dp)
                    .align(Alignment.End)
                    .clickable { onProfileClick() },
            )

            UserInfoSection(name = uiState.name, photo = uiState.profilePhoto)

            OrganizationListSection(
                pagerState = pagerState,
                organizationList = organizationLists,
                onClassClick = onClassClick
            )

            Spacer(modifier = Modifier.weight(1f))

            NewClassButton(
                Modifier
                    .padding(18.dp, 28.dp)
                    .align(Alignment.End),
                onNewClassClick = onNewClassClick
            )

        }
        if (uiState.isLoading) {
            LoadingScreen()
        }
    }

}

@Composable
fun UserInfoSection(name: String, photo: String) {
    Column {
        AsyncImage(
            model = photo,
            contentDescription = "profile image",
            placeholder = ColorPainter(Color.Blue),
            modifier = Modifier
                .padding(20.dp)
                .size(200.dp)
        )

        Text(
            text = "$name 선생님 환영합니다",
            style = UlbanTypography.titleMedium,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 60.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrganizationListSection(
    pagerState: PagerState,
    organizationList: List<Organization>,
    onClassClick: (Int) -> Unit
) {
    val backgroundColorList = listOf(Red, Blue, Orange, Yellow, Green, Purple)

    if (organizationList.isEmpty()) {
        Text(
            text = "학급이 없습니다",
            style = UlbanTypography.titleMedium,
        )
    } else {
        HorizontalPager(state = pagerState) {
            organizationList.forEachIndexed { idx, item ->
                val name = item.name.split("\n")
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(200.dp)
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(idx).absoluteValue

                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                        .clickable { onClassClick(item.id) },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColorList[idx % backgroundColorList.size]),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp, 40.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = name[0], style = UlbanTypography.titleMedium)
                        Text(
                            text = name[1],
                            style = UlbanTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = com.sixkids.designsystem.R.drawable.member),
                                contentDescription = "member count"
                            )

                            Text(
                                text = "${item.memberCount}명",
                                style = UlbanTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewClassButton(
    modifier: Modifier = Modifier,
    onNewClassClick: () -> Unit
) {
    Button(
        onClick = { onNewClassClick() },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(Blue, contentColor = BlueDark),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 8.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = "new class")
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "학급 추가",
                style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ClassListScreenPreview() {
    OrganizationListScreen()
}

