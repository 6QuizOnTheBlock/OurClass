package com.sixkids.teacher.challenge.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.teacher.challenge.create.ChallengeCreateRoute
import com.sixkids.teacher.challenge.detail.ChallengeDetailRoute
import com.sixkids.teacher.challenge.history.ChallengeRoute
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.CHALLENGE_ID_NAME
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.GROUP_ID_NAME
import com.sixkids.ui.SnackbarToken

fun NavController.navigateChallengeHistory() {
    navigate(ChallengeRoute.defaultRoute)
}

fun NavController.navigateChallengeDetail(challengeId: Long, groupId: Long?) {
    navigate(ChallengeRoute.detailRoute(challengeId, groupId))
}

fun NavController.navigateCreateChallenge() {
    navigate(ChallengeRoute.createRoute)
}

fun NavGraphBuilder.challengeNavGraph(
    navigateChallengeDetail: (Long, Long?) -> Unit,
    navigateCreateChallenge: () -> Unit,
    navigateUp: () -> Unit,
    showSnackbar: (SnackbarToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = ChallengeRoute.defaultRoute) {
        ChallengeRoute(
            navigateToDetail = { challengeId, groupId ->
                navigateChallengeDetail(challengeId, groupId)
            },
            navigateToCreate = navigateCreateChallenge,
            handleException = handleException
        )
    }

    composable(
        route = ChallengeRoute.detailRoute,
        arguments = listOf(
            navArgument(CHALLENGE_ID_NAME) { type = NavType.LongType },
            navArgument(GROUP_ID_NAME) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        ChallengeDetailRoute(
            handleException = handleException,
        )
    }

    composable(route = ChallengeRoute.createRoute) {
        ChallengeCreateRoute(
            onShowSnackbar = showSnackbar,
            onNavigateUp = navigateUp
        )
    }

}

object ChallengeRoute {
    const val CHALLENGE_ID_NAME = "challengeId"
    const val GROUP_ID_NAME = "groupId"
    const val defaultRoute = "challenge-history"
    const val createRoute = "challenge-create"
    const val detailRoute = "challenge-detail?challengeId={$CHALLENGE_ID_NAME}&groupId={${GROUP_ID_NAME}}"
    fun detailRoute(challengeId: Long, groupId: Long?) = "challenge-detail?challengeId=$challengeId&groupId=${groupId}"
}
