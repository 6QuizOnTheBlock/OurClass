package com.sixkids.teacher.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.challenge.history.ChallengeRoute
import com.sixkids.teacher.home.main.HomeMainRoute
import com.sixkids.teacher.home.rank.RankRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.defaultRoute, navOptions)
}

fun NavController.navigateRank() {
    navigate(HomeRoute.rankRoute)
}

fun NavController.navigateChallenge() {
    navigate(HomeRoute.challengeRoute)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    navigateToRank: () -> Unit,
    navigateToChallenge: () -> Unit,
) {
    composable(route = HomeRoute.defaultRoute) {
        HomeMainRoute(
            padding,
            navigateToRank,
            navigateToChallenge
        )
    }
    composable(route = HomeRoute.rankRoute) {
        RankRoute(padding)
    }

    composable(route = HomeRoute.challengeRoute) {
        ChallengeRoute(
            padding = padding,
        )
    }
}



object HomeRoute {
    const val defaultRoute = "home"
    const val rankRoute = "rank"
    const val challengeRoute = "challenge"
}
