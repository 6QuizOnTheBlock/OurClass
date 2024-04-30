package com.sixkids.teacher.challenge.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.teacher.challenge.detail.ChallengeDetailRoute
import com.sixkids.teacher.challenge.history.ChallengeRoute
import com.sixkids.teacher.challenge.navigation.ChallengeRoute.CHALLENGE_ID_NAME

fun NavController.navigateChallengeHistory() {
    navigate(ChallengeRoute.defaultRoute)
}

fun NavController.navigateChallengeDetail(challengeId: Int) {
    navigate(ChallengeRoute.detailRoute(challengeId))
}

fun NavController.navigateCreateChallenge() {
    navigate(ChallengeRoute.createRoute)
}

fun NavGraphBuilder.challengeNavGraph(
    padding: PaddingValues,
    navigateChallengeDetail: (Int) -> Unit,
    navigateCreateChallenge: () -> Unit,
) {
    composable(route = ChallengeRoute.defaultRoute) {
        ChallengeRoute(
            navigateToDetail = { challengeId ->
                navigateChallengeDetail(challengeId)
            },
            navigateToCreate = navigateCreateChallenge
        )
    }

    composable(
        route = ChallengeRoute.detailRoute,
        arguments = listOf(navArgument(CHALLENGE_ID_NAME) { type = NavType.IntType })
    ) {
        ChallengeDetailRoute()
    }

}

object ChallengeRoute {
    const val CHALLENGE_ID_NAME = "challengeId"

    const val defaultRoute = "challenge-history"
    const val createRoute = "challenge-create"
    const val detailRoute = "challenge-detail/{$CHALLENGE_ID_NAME}"

    fun detailRoute(challengeId: Int) = "challenge-detail/$challengeId"
}
