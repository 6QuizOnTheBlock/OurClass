package com.sixkids.teacher.managestudent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.managestudent.main.ManageStudentMainRoute

fun NavController.navigateManageStudent(navOptions: NavOptions) {
    navigate(ManageStudentRoute.defaultRoute,navOptions)
}

fun NavGraphBuilder.manageStudentNavGraph(
    padding: PaddingValues
) {
    composable(route = ManageStudentRoute.defaultRoute) {
        ManageStudentMainRoute(padding)
    }
}

object ManageStudentRoute {
    const val defaultRoute = "manage_student"
}