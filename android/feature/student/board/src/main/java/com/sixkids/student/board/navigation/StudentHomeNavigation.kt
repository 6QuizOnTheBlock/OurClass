package com.sixkids.student.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.student.board.main.StudentHomeMainRoute

fun NavController.navigateStudentHome(navOptions: NavOptions) {
    navigate(StudentHomeRoute.defaultRoute,navOptions)
}

fun NavGraphBuilder.studentHomeNavGraph(
    padding: PaddingValues,
) {
    composable(route = StudentHomeRoute.defaultRoute) {
        StudentHomeMainRoute(padding)
    }
}

object StudentHomeRoute {
    const val defaultRoute = "student_home"
}