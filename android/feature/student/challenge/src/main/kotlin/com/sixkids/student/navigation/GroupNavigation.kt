package com.sixkids.student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.student.group.create.CreateGroupRoute
import com.sixkids.student.group.join.JoinGroupRoute

fun NavController.navigateStudentGroupCreate() {
    navigate(GroupRoute.creatGroupRoute)
}

fun NavController.navigateStudentGroupJoin() {
    navigate(GroupRoute.joinGroupRoute)
}

fun NavGraphBuilder.studentGroupNavGraph(
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = GroupRoute.creatGroupRoute) {
        CreateGroupRoute()
    }
    composable(route = GroupRoute.joinGroupRoute) {
        JoinGroupRoute()
    }
}

object GroupRoute {
    const val creatGroupRoute = "student/group/create"
    const val joinGroupRoute = "student/group/join"
}
