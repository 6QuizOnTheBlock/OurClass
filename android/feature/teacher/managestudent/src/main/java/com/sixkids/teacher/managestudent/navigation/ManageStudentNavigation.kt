package com.sixkids.teacher.managestudent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.teacher.managestudent.detail.ManageStudentDetailEffect
import com.sixkids.teacher.managestudent.detail.ManageStudentDetailRoute
import com.sixkids.teacher.managestudent.main.ManageStudentMainEffect
import com.sixkids.teacher.managestudent.main.ManageStudentMainRoute
import com.sixkids.teacher.managestudent.navigation.ManageStudentRoute.STUDENT_ID_NAME

fun NavController.navigateManageStudent(navOptions: NavOptions) {
    navigate(ManageStudentRoute.defaultRoute,navOptions)
}

fun NavController.navigateStudentDetail(studentId: Long) {
    navigate(ManageStudentRoute.studentDetailRoute(studentId))
}

fun NavGraphBuilder.manageStudentNavGraph(
    padding: PaddingValues,
    navigateToStudentDetail: (Long) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = ManageStudentRoute.defaultRoute) {
        ManageStudentMainRoute(
            padding,
            navigateToStudentDetail = navigateToStudentDetail,
            handleException = handleException
        )
    }

    composable(route = ManageStudentRoute.studentDetailRoute,
        arguments = listOf(
            navArgument(STUDENT_ID_NAME) { type = NavType.LongType },
            ))
    {
        ManageStudentDetailRoute(

        )
    }
}

object ManageStudentRoute {
    const val STUDENT_ID_NAME = "studentId"

    const val defaultRoute = "manage_student"
    const val studentDetailRoute = "student-detail?studentId={$STUDENT_ID_NAME}"

    fun studentDetailRoute(relayId: Long) = "student-detail?studentId=$relayId"
}