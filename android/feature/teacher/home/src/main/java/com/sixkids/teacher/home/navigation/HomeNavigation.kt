package com.sixkids.teacher.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.home.main.HomeMainRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.defaultRoute,navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
) {
    composable(route = HomeRoute.defaultRoute){
        HomeMainRoute(padding)
    }
}

object HomeRoute{
    const val defaultRoute = "home"
}