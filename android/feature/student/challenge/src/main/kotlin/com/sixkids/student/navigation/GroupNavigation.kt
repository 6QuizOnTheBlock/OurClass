package com.sixkids.student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sixkids.model.GroupType
import com.sixkids.student.group.create.CreateGroupRoute
import com.sixkids.student.group.join.JoinGroupRoute

fun NavController.navigateStudentGroupCreate(
    challengeId: Long,
    groupType: GroupType
) {
    navigate(GroupRoute.createGroupRoute(challengeId, groupType))
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

    const val CHALLENGE_ID_NAME = "challengeId"
    const val GROUP_TYPE_NAME = "groupType"

    const val creatGroupRoute = "student/group/create?challengeId={$CHALLENGE_ID_NAME}?groupType={$GROUP_TYPE_NAME}"
    const val joinGroupRoute = "student/group/join"

    fun createGroupRoute(challengeId: Long, groupType: GroupType): String {
        return "student/group/create?challengeId=$challengeId?groupType=$groupType"
    }
}
