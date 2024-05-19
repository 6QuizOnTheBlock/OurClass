package com.sixkids.student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.model.GroupType
import com.sixkids.model.MemberSimple
import com.sixkids.student.group.create.free.CreateGroupRoute
import com.sixkids.student.group.create.matched.MatchedCreateGroupRoute
import com.sixkids.student.group.join.JoinGroupRoute

fun NavController.navigateStudentGroupCreate(
    challengeId: Long,
    groupType: GroupType
) {
    navigate(GroupRoute.createGroupRoute(challengeId, groupType))
}

fun NavController.navigateStudentMatchedGroupCreate(
    challengeId: Long,
    members: List<MemberSimple>
) {
    navigate(GroupRoute.matchedGroupCreateRoute(challengeId, members))
}


fun NavController.navigateStudentGroupJoin() {
    navigate(GroupRoute.joinGroupRoute)
}

fun NavGraphBuilder.studentGroupNavGraph(
    navigateToChallengeHistory: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(
        route = GroupRoute.creatGroupRoute,
        arguments = listOf(navArgument(GroupRoute.CHALLENGE_ID_NAME) { type = NavType.LongType })
    ) {
        CreateGroupRoute(
            navigateToChallengeHistory = navigateToChallengeHistory,
            handleException = handleException
        )
    }
    composable(route = GroupRoute.joinGroupRoute) {
        JoinGroupRoute(
            navigateToChallengeHistory = navigateToChallengeHistory,
            handleException = handleException
        )
    }
    composable(route = GroupRoute.matchedGroupCreateRoute) {
        MatchedCreateGroupRoute(
            navigateToChallengeHistory = navigateToChallengeHistory,
            handleException = handleException
        )

    }
}

object GroupRoute {

    const val CHALLENGE_ID_NAME = "challengeId"
    const val GROUP_TYPE_NAME = "groupType"
    const val MEMBERS_NAME = "members"

    const val creatGroupRoute =
        "student/group/create?challengeId={$CHALLENGE_ID_NAME}?groupType={$GROUP_TYPE_NAME}"
    const val matchedGroupCreateRoute =
        "student/group/matched-create?challengeId=$CHALLENGE_ID_NAME?members=$MEMBERS_NAME"
    const val joinGroupRoute = "student/group/join"

    fun createGroupRoute(challengeId: Long, groupType: GroupType): String {
        return "student/group/create?challengeId=$challengeId?groupType=$groupType"
    }

    fun matchedGroupCreateRoute(challengeId: Long, members: List<MemberSimple>): String {
        return "student/group/matched-create?challengeId=$challengeId?members=$members"
    }
}
