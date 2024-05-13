package com.sixkids.student.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.student.home.announce.announcedetail.StudentAnnounceDetailEffect
import com.sixkids.student.home.announce.announcedetail.StudentAnnounceDetailRoute
import com.sixkids.student.home.announce.announcelist.StudentAnnounceListRoute
import com.sixkids.student.home.chatting.StudentChattingRoute
import com.sixkids.student.home.main.StudentHomeMainRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateStudentHome(navOptions: NavOptions) {
    navigate(StudentHomeRoute.defaultRoute,navOptions)
}

fun NavController.navigateStudentAnnounceList() {
    navigate(StudentHomeRoute.announceListRoute)
}

fun NavController.navigateStudentAnnounceDetail(announceDetailId: Long) {
    navigate(StudentHomeRoute.announceDetailRoute(announceDetailId))
}

fun NavController.navigateStudentChatting() {
    navigate(StudentHomeRoute.chattingRoute)
}

fun NavGraphBuilder.studentHomeNavGraph(
    padding: PaddingValues,
    onShowSnackbar: (SnackbarToken) -> Unit,
    navigateBack: () -> Unit,
    navigateToStudentAnnounceList: () -> Unit,
    navigateToStudentAnnounceDetail: (Long) -> Unit,
    navigateToTagHello: () -> Unit,
    navigateToRank: () -> Unit,
    navigateToChatting: () -> Unit,
) {
    composable(route = StudentHomeRoute.defaultRoute) {
        StudentHomeMainRoute(
            padding = padding,
            navigateToAnnounce = navigateToStudentAnnounceList,
            navigateToTagHello = navigateToTagHello,
            navigateToRank = navigateToRank,
            navigateToChatting = navigateToChatting,
            onShowSnackBar = onShowSnackbar
        )
    }

    composable(route = StudentHomeRoute.announceListRoute) {
        StudentAnnounceListRoute(
            padding = padding,
            navigateToStudentAnnounceDetail = navigateToStudentAnnounceDetail,
            onShowSnackBar = onShowSnackbar
        )
    }

    composable(
        route = StudentHomeRoute.announceDetailRoute,
        arguments = listOf(navArgument(StudentHomeRoute.announceDetailARG) { type = NavType.LongType })
    ) {
        StudentAnnounceDetailRoute(
            padding = padding,
            onShowSnackBar = onShowSnackbar
        )
    }

    composable(route = StudentHomeRoute.chattingRoute) {
        StudentChattingRoute(
            onBackClick = navigateBack,
            onShowSnackBar = onShowSnackbar
        )
    }
}

object StudentHomeRoute {
    const val announceDetailARG = "announceDetailId"

    const val defaultRoute = "student_home"

    const val announceListRoute = "student_announce_list"
    const val announceDetailRoute = "student_announce_detail/{$announceDetailARG}"

    const val chattingRoute = "student_chatting"

    fun announceDetailRoute(announceDetailId: Long) = "student_announce_detail/$announceDetailId"
}