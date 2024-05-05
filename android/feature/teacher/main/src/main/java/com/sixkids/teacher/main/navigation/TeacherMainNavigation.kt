package com.sixkids.teacher.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.teacher.main.organization.OrganizationListRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateOrganizationList() {
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
    onShowSnackBar: (SnackbarToken) -> Unit,
) {
    composable(route = TeacherMainRoute.defaultRoute){
        OrganizationListRoute(
            navigateToNewClass = navigateToNewOrganization,
            navigateToProfile = navigateToProfile,
            navigateToHome = navigateToHome,
            onShowSnackBar = onShowSnackBar
        )
    }

}

object TeacherMainRoute{
    const val defaultRoute = "organization-list"
    const val newOrganizationRoute = "new-organization"
    const val profileRoute = "profile"
}