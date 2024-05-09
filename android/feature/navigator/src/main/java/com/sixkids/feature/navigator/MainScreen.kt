package com.sixkids.feature.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sixkids.designsystem.component.snackbar.UlbanSnackbar
import com.sixkids.designsystem.theme.Cream
import com.sixkids.feature.signin.navigation.signInNavGraph
import com.sixkids.student.board.navigation.studentHomeNavGraph
import com.sixkids.student.navigation.studentChallengeNavGraph
import com.sixkids.student.navigation.studentGroupNavGraph
import com.sixkids.teacher.board.navigation.boardNavGraph
import com.sixkids.teacher.challenge.navigation.challengeNavGraph
import com.sixkids.teacher.home.navigation.homeNavGraph
import com.sixkids.teacher.main.navigation.teacherOrganizationListNavGraph
import com.sixkids.teacher.manageclass.navigation.manageClassNavGraph
import com.sixkids.teacher.managestudent.navigation.manageStudentNavGraph
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    navigator: MainNavigator = rememberMainNavigator()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is MainSideEffect.ShowSnackbar -> {
                viewModel.onShowSnackbar(uiState.snackbarToken)
            }
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermission()
    }

    Scaffold(
        bottomBar = {
            BottomNav(
                visible = navigator.shouldShowBottomBar(),
                modifier = Modifier,
                selectedTab = navigator.currentTab ?: MainNavigationTab.HOME,
                itemClick = navigator::navigate,
                bottomTavItems = navigator.bottomTabItems?.value
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        )
        {
            homeNavGraph(
                padding = innerPadding,
                navigateToRank = navigator::navigateRank,
                navigateToChallenge = navigator::navigateChallengeHistory,
            )

            boardNavGraph(
                padding = innerPadding,
                navigateToPost = navigator::navigatePost,
                navigateToPostDetail = navigator::navigatePostDetail,
                onBackClick = navigator::popBackStack,
                onShowSnackBar = viewModel::onShowSnackbar,
                navigateToChatting = navigator::navigateChatting,
            )

            challengeNavGraph(
                navigateChallengeDetail = navigator::navigateChallengeDetail,
                navigateCreateChallenge = navigator::navigateCreateChallenge,
                navigateChallengeCreatedResult = navigator::navigateChallengeCreatedResult,
                navigateChallengeHistory = navigator::navigatePopupToHistory,
                handleException = viewModel::handleException,
                showSnackbar = viewModel::onShowSnackbar,
                navigateUp = navigator::popBackStack,
            )

            manageClassNavGraph(
                padding = innerPadding,
            )

            manageStudentNavGraph(
                padding = innerPadding,
            )

            signInNavGraph(
                navigateToSignUp = navigator::navigateSignUp,
                navigateSignUpPhoto = navigator::navigateSignUpPhoto,
                navigateToHome = navigator::navigateHome,
                onShowSnackBar = viewModel::onShowSnackbar,
                onBackClick = navigator::popBackStack,
                navigateToTeacherOrganizationList = navigator::navigateTeacherOrganizationList,
                navigateToStudentHome = navigator::navigateStudentHome,
            )

            teacherOrganizationListNavGraph(
                navigateToNewOrganization = navigator::navigateNewOrganization,
                navigateToProfile = navigator::navigateProfile,
                navigateToHome = navigator::navigateHome,
                onShowSnackBar = viewModel::onShowSnackbar,
                onBackClick = navigator::popBackStack,
                navigateToSignIn = navigator::navigateSignIn,
            )

            studentHomeNavGraph(
                padding = innerPadding,
            )
            studentChallengeNavGraph(
                navigateChallengeDetail = navigator::navigateChallengeDetail,
                navigateToCreateGroup = navigator::navigateStudentGroupCreate,
                navigateToJoinGroup = navigator::navigateStudentGroupJoin,
                handleException = viewModel::handleException,
            )

            studentGroupNavGraph(
                handleException = viewModel::handleException,
            )
        }
        with(uiState) {
            UlbanSnackbar(
                modifier = Modifier.padding(innerPadding),
                visible = snackbarVisible,
                message = snackbarToken.message,
                actionIconId = snackbarToken.actionIcon,
                actionButtonText = snackbarToken.actionButtonText,
                onClickActionButton = snackbarToken.onClickActionButton,
            )
        }
    }
}


@Composable
fun BottomNav(
    visible: Boolean,
    modifier: Modifier,
    itemClick: (MainNavigationTab) -> Unit = {},
    selectedTab: MainNavigationTab,
    bottomTavItems: List<MainNavigationTab>? = null
) {
    val selectedItem = rememberUpdatedState(newValue = selectedTab)

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
    ) {
        Surface(
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            NavigationBar(
                modifier = modifier,
                containerColor = Cream,
            ) {
                bottomTavItems?.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = item.iconId),
                                contentDescription = null,
                                tint = item.iconTint
                            )
                        },

                        label = {
                            Text(text = stringResource(id = item.labelId))
                        },
                        selected = selectedItem.value == item,
                        onClick = {
                            itemClick(item)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission() {
    val notificationPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(Unit) {
        if (notificationPermissionState.status.isGranted.not()) {
            notificationPermissionState.launchPermissionRequest()
        }
    }

}
