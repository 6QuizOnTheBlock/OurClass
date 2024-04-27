package com.sixkids.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.defaultRoute,navOptions)
}

object HomeRoute{
    const val defaultRoute = "home"
}