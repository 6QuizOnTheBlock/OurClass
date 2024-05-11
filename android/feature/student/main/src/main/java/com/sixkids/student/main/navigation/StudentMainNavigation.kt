package com.sixkids.student.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.student.main.joinorganization.JoinOrganizationRoute
import com.sixkids.student.main.organization.StudentOrganizationListRoute
import com.sixkids.student.main.profile.StudentProfileRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateStudentOrganizationList() {
    navigate(StudentMainRoute.defaultRoute)
}

fun NavController.navigateStudentProfile(){
    navigate(StudentMainRoute.profileRoute)
}

fun NavController.navigateJoinOrganization(){
    navigate(StudentMainRoute.joinOrganizationRoute)
}

fun NavGraphBuilder.studentOrganizationListNavGraph(
    navigateToJoinOrganization: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSignIn: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    onBackClick: () -> Unit
) {
    composable(route = StudentMainRoute.defaultRoute) {
        StudentOrganizationListRoute(
            navigateToJoinOrganization = navigateToJoinOrganization,
            navigateToProfile = navigateToProfile,
            navigateToHome = navigateToHome,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = StudentMainRoute.joinOrganizationRoute) {
        JoinOrganizationRoute(
            navigateToStudentOrganizationList = onBackClick,
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = StudentMainRoute.profileRoute) {
        StudentProfileRoute(
            navigateToSignIn = navigateToSignIn,
            navigateToStudentOrganizationList = onBackClick,
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar
        )
    }
}

object StudentMainRoute {
    const val defaultRoute = "student-organization-list"
    const val joinOrganizationRoute = "join-organization"
    const val profileRoute = "student-profile"
}