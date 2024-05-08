package com.sixkids.teacher.challenge.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.teacher.challenge.create.ChallengeCreateRoute
import com.sixkids.teacher.challenge.detail.ChallengeDetailRoute
import com.sixkids.teacher.challenge.history.ChallengeRoute
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.CHALLENGE_ID_NAME
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.CHALLENGE_TITLE_NAME
import com.sixkids.teacher.challenge.result.ResultRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateChallengeHistory() {
    navigate(ChallengeRoute.defaultRoute)
}

fun NavController.navigatePopupToHistory() {
    navigate(ChallengeRoute.defaultRoute) {
        popUpTo(ChallengeRoute.defaultRoute) {
            inclusive = true
        }
    }
}

fun NavController.navigateChallengeDetail(challengeId: Int) {
    navigate(ChallengeRoute.detailRoute(challengeId))
}

fun NavController.navigateCreateChallenge() {
    navigate(ChallengeRoute.createRoute)
}

fun NavController.navigateChallengeCreatedResult(challengeId: Int, title: String) {
    navigate(ChallengeRoute.resultRoute(challengeId, title)) {
        popUpTo(ChallengeRoute.defaultRoute) {
            inclusive = false
        }
    }
    navigate(ChallengeRoute.resultRoute(challengeId, title))
}

fun NavGraphBuilder.challengeNavGraph(
    padding: PaddingValues,
    navigateChallengeHistory: () -> Unit,
    navigateChallengeDetail: (Int) -> Unit,
    navigateChallengeCreatedResult: (Int, String) -> Unit,
    navigateCreateChallenge: () -> Unit,
    navigateUp: () -> Unit,
    showSnackbar: (SnackbarToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = ChallengeRoute.defaultRoute) {
        ChallengeRoute(
            navigateToDetail = { challengeId ->
                navigateChallengeDetail(challengeId)
            },
            navigateToCreate = navigateCreateChallenge,
            handleException = handleException
        )
    }

    composable(
        route = ChallengeRoute.detailRoute,
        arguments = listOf(navArgument(CHALLENGE_ID_NAME) { type = NavType.IntType })
    ) {
        ChallengeDetailRoute()
    }

    composable(route = ChallengeRoute.createRoute) {
        ChallengeCreateRoute(
            onShowSnackbar = showSnackbar,
            onNavigateUp = navigateUp,
            onNavigateResult = { challengeId, title ->
                navigateChallengeCreatedResult(challengeId, title)
            },
            onHandleException = handleException
        )
    }
    composable(
        route = ChallengeRoute.resultRoute,
        arguments = listOf(
            navArgument(CHALLENGE_ID_NAME) { type = NavType.IntType },
            navArgument(CHALLENGE_TITLE_NAME) { type = NavType.StringType }
        )
    ) {
        ResultRoute(
            navigateToChallengeHistory = navigateChallengeHistory,
            handleException = handleException
        )
    }

}

object ChallengeRoute {
    const val CHALLENGE_ID_NAME = "challengeId"
    const val CHALLENGE_TITLE_NAME = "challengeTitle"
    const val defaultRoute = "challenge-history"
    const val createRoute = "challenge-create"
    const val detailRoute = "challenge-detail/{$CHALLENGE_ID_NAME}"
    const val resultRoute = "challenge-create-result/{$CHALLENGE_ID_NAME}/{$CHALLENGE_TITLE_NAME}"
    fun detailRoute(challengeId: Int) = "challenge-detail/$challengeId"
    fun resultRoute(challengeId: Int, title: String) =
        "challenge-create-result/$challengeId/$title"
}
