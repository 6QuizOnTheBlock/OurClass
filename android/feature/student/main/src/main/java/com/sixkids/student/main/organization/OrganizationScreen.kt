package com.sixkids.student.main.organization


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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
import com.sixkids.student.main.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import kotlin.math.absoluteValue

private const val TAG = "D107"
@Composable
fun StudentOrganizationListRoute(
    viewModel: OrganizationViewModel = hiltViewModel(),
    navigateToJoinOrganization: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToHome: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            OrganizationListEffect.NavigateToJoinClass -> navigateToJoinOrganization()
            OrganizationListEffect.NavigateToProfile -> navigateToProfile()
            OrganizationListEffect.NavigateToHome -> navigateToHome()
            is OrganizationListEffect.OnShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    OrganizationListScreen(
        uiState = uiState,
        onJoinClassClick = viewModel::joinOrganizationClick,
        onProfileClick = viewModel::profileClick,
        onClassClick = { classId ->
            viewModel.organizationClick(classId)
        }
    )



    FirebaseMessaging.getInstance().token.addOnCompleteListener(
        OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            if (task.result != null) {
                viewModel.onTokenRefresh(task.result)
            }
        },
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrganizationListScreen(
    uiState: OrganizationListState = OrganizationListState(),
    onJoinClassClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onClassClick: (Int) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val pagerState = rememberPagerState(pageCount = { uiState.organizationList.size })
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "profile",
                modifier = Modifier
                    .padding(24.dp)
                    .size(40.dp)
                    .align(Alignment.End)
                    .clickable { onProfileClick() },
            )

            StudentUserInfoSection(name = uiState.name, photo = uiState.profilePhoto)

            OrganizationListSection(
                pagerState = pagerState,
                organizationList = uiState.organizationList,
                onClassClick = onClassClick
            )

            Spacer(modifier = Modifier.weight(1f))

            NewClassButton(
                Modifier
                    .padding(18.dp, 28.dp)
                    .align(Alignment.End),
                onNewClassClick = onJoinClassClick
            )

        }
        if (uiState.isLoading) {
            LoadingScreen()
        }
    }

}

@Composable
fun StudentUserInfoSection(name: String, photo: String) {
    Log.d(TAG, "UserInfoSection: ")
    Column {
        AsyncImage(
            model = photo,
            contentDescription = "profile image",
            modifier = Modifier
                .padding(20.dp)
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = String.format(stringResource(id = R.string.student_organization_welcome), name).also {
                Log.d(TAG, "UserInfoSection: $it")
            },
            style = UlbanTypography.titleMedium,
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 60.dp)
                .align(Alignment.CenterHorizontally)
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

    val screenWidthDp = with(LocalDensity.current) {
        LocalContext.current.resources.displayMetrics.widthPixels.toDp()
    }
    val cardWidth = 220.dp
    val horizontalPadding = (screenWidthDp - cardWidth) / 2

    if (organizationList.isEmpty()) {
        Text(
            text = stringResource(id = R.string.student_organization_no_organization),
            style = UlbanTypography.titleMedium,
        )
    } else {

        HorizontalPager(
            pageSpacing = 10.dp,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            modifier = Modifier.fillMaxWidth()
        ) {
            val item = organizationList[it]
            val name = item.name.split("\n")
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .size(cardWidth)
                    .clickable { onClassClick(item.id) }
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - it) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColorList[it % backgroundColorList.size]),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                text = stringResource(id = R.string.student_organization_join_class),
                style = UlbanTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrganizationListScreenPreview() {
    OrganizationListScreen()
}
