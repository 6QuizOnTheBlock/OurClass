package com.sixkids.teacher.managestudent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.managestudent.main.ManageStudentMainEffect
import com.sixkids.teacher.managestudent.main.ManageStudentMainRoute

fun NavController.navigateManageStudent(navOptions: NavOptions) {
    navigate(ManageStudentRoute.defaultRoute,navOptions)
}

fun NavController.navigateStudentDetail(studentId: Long) {
    navigate(ManageStudentRoute.studentDetailRoute(studentId))
}

fun NavGraphBuilder.manageStudentNavGraph(
    padding: PaddingValues,
    navigateToStudentDetail: (Long) -> Unit
) {
    composable(route = ManageStudentRoute.defaultRoute) {
        ManageStudentMainRoute(
            padding,
            navigateToStudentDetail = navigateToStudentDetail
        )
    }
}

object ManageStudentRoute {
    const val STUDENT_ID_NAME = "relayId"

    const val defaultRoute = "manage_student"
    const val studentDetailRoute = "student-detail?relayId={$STUDENT_ID_NAME}"

    fun studentDetailRoute(relayId: Long) = "student-detail?relayId=$relayId"
}