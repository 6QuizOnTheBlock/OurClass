package com.sixkids.student.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

fun NavController.navigateStudentHome(navOptions: NavOptions) {
    navigate(StudentHomeRoute.defaultRoute,navOptions)
}

fun NavGraphBuilder.studentHomeNavGraph(
    padding: PaddingValues,
) {
    composable(route = StudentHomeRoute.defaultRoute) {
        com.sixkids.student.home.main.StudentHomeMainRoute(padding)
    }
}

object StudentHomeRoute {
    const val defaultRoute = "student_home"
}