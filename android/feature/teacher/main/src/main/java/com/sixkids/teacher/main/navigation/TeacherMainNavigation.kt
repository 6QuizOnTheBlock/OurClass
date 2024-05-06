package com.sixkids.teacher.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.teacher.main.organization.OrganizationListRoute
import com.sixkids.teacher.main.profile.TeacherProfileRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateTeacherOrganizationList() {
    navigate(TeacherMainRoute.defaultRoute)
}

fun NavController.navigateNewOrganization() {
    navigate(TeacherMainRoute.newOrganizationRoute)
}

fun NavController.navigateProfile() {
    navigate(TeacherMainRoute.profileRoute)
}

fun NavGraphBuilder.teacherOrganizationListNavGraph(
    navigateToNewOrganization: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSignIn: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    onBackClick: () -> Unit
) {
    composable(route = TeacherMainRoute.defaultRoute) {
        OrganizationListRoute(
            navigateToNewClass = navigateToNewOrganization,
            navigateToProfile = navigateToProfile,
            navigateToHome = navigateToHome,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = TeacherMainRoute.profileRoute) {
        TeacherProfileRoute(
            navigateToOrganizationList = onBackClick,
            navigateToSignIn = navigateToSignIn,
            onShowSnackBar = onShowSnackBar,
            onBackClick = onBackClick
        )
    }

}

object TeacherMainRoute {
    const val defaultRoute = "organization-list"
    const val newOrganizationRoute = "new-organization"
    const val profileRoute = "profile"
}