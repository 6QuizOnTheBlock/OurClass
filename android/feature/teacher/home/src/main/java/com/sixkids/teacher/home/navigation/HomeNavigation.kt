package com.sixkids.teacher.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.home.main.HomeMainRoute
import com.sixkids.teacher.home.rank.RankRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.defaultRoute, navOptions)
}

fun NavController.navigateRank() {
    navigate(HomeRoute.rankRoute)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit,
    navigateToRank: () -> Unit,
    navigateToChallenge: () -> Unit,
    navigateToRelay: () -> Unit,
    navigateToQuiz: () -> Unit
) {
    composable(route = HomeRoute.defaultRoute) {
        HomeMainRoute(
            padding = padding,
            navigateToRank = navigateToRank,
            navigateToChallenge = navigateToChallenge,
            navigateToRelay = navigateToRelay,
            navigateToQuiz = navigateToQuiz
        )
    }
    composable(route = HomeRoute.rankRoute) {
        RankRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar
        )
    }
}


object HomeRoute {
    const val defaultRoute = "home"
    const val rankRoute = "rank"
}
