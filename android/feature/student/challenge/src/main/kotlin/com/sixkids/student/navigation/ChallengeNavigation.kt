package com.sixkids.student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.student.challeng.history.ChallengeRoute

fun NavController.navigateStudentChallengeHistory(navOptions: NavOptions) {
    navigate(ChallengeRoute.defaultRoute, navOptions)
}



fun NavGraphBuilder.studentChallengeNavGraph(
    navigateChallengeDetail: (Long, Long?) -> Unit,
    navigateToCreateGroup: (Long) -> Unit,
    navigateToJoinGroup: (Long) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = ChallengeRoute.defaultRoute) {
        ChallengeRoute(
            navigateToDetail = { challengeId, groupId ->
                navigateChallengeDetail(challengeId, groupId)
            },
            navigateToCreateGroup = navigateToCreateGroup,
            navigateToJoinGroup = navigateToJoinGroup,
            handleException = handleException
        )
    }

}

object ChallengeRoute {
    const val defaultRoute = "student/challenge-history"
}
