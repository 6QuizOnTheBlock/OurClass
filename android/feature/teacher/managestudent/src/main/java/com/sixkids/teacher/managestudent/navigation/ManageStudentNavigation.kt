package com.sixkids.teacher.managestudent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.teacher.managestudent.main.ManageStudentMainRoute

fun NavController.navigateManageStudent() {
    navigate(ManageStudentRoute.defaultRoute)
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