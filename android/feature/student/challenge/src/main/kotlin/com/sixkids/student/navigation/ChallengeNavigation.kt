package com.sixkids.student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.model.GroupType
import com.sixkids.model.MemberSimple
import com.sixkids.student.challeng.history.ChallengeRoute

fun NavController.navigateStudentChallengeHistory(navOptions: NavOptions) {
    navigate(ChallengeRoute.defaultRoute, navOptions)
}

fun NavController.navigatePopupToStudentChallengeHistory() {
    navigate(ChallengeRoute.defaultRoute) {
        popUpTo(ChallengeRoute.defaultRoute) {
            inclusive = true
        }
    }
}


fun NavGraphBuilder.studentChallengeNavGraph(
    navigateChallengeDetail: (Long, Long?) -> Unit,
    navigateToCreateGroup: (Long, GroupType) -> Unit,
    navigateToMatchedGroupCreate: (Long, List<MemberSimple>) -> Unit,
    navigateToJoinGroup: (Long) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = ChallengeRoute.defaultRoute) {
        ChallengeRoute(
            navigateToDetail = { challengeId, groupId ->
                navigateChallengeDetail(challengeId, groupId)
            },
            navigateToCreateGroup = navigateToCreateGroup,
            navigateToMatchedGroupCreate = navigateToMatchedGroupCreate,
            navigateToJoinGroup = navigateToJoinGroup,
            handleException = handleException
        )
    }

}

object ChallengeRoute {
    const val defaultRoute = "student/challenge-history"
}
