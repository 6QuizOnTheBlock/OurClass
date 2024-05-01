package com.sixkids.teacher.manageclass.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.teacher.manageclass.main.ManageClassMainRoute

fun NavController.navigateManageClass() {
    navigate(ManageClassRoute.defaultRoute)
}

fun NavGraphBuilder.manageClassNavGraph(
    padding: PaddingValues
) {
    composable(route = ManageClassRoute.defaultRoute){
        ManageClassMainRoute(padding)
    }
}

object ManageClassRoute {
    const val defaultRoute = "manage_class"
}