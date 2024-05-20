package com.sixkids.student.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.student.board.detail.StudentBoardDetailRoute
import com.sixkids.student.board.main.StudentBoardMainRoute
import com.sixkids.student.board.write.StudentBoardWriteRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateStudentBoard(navOptions: NavOptions) {
    navigate(StudentBoardRoute.defaultRoute,navOptions)
}

fun NavController.navigateStudentBoardWrite() {
    navigate(StudentBoardRoute.writeRoute)
}

fun NavController.navigateStudentBoardDetail(postId: Long) {
    navigate(StudentBoardRoute.detailRoute(postId))
}

fun NavGraphBuilder.studentBoardNavGraph(
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit,
    navigateToStudentBoardDetail: (postId:Long) -> Unit,
    navigateToStudentBoardWrite: () -> Unit,
    navigateBack: () -> Unit
) {
    composable(route = StudentBoardRoute.defaultRoute) {
        StudentBoardMainRoute(
            padding = padding,
            navigateToStudentBoardDetail = navigateToStudentBoardDetail,
            navigateToStudentBoardWrite = navigateToStudentBoardWrite,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = StudentBoardRoute.writeRoute) {
        StudentBoardWriteRoute(
            padding = padding,
            navigateBack = navigateBack,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(
        route = StudentBoardRoute.detailRoute,
        arguments = listOf(navArgument(StudentBoardRoute.postDetailARG) { type = NavType.LongType })
    ) {
        StudentBoardDetailRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar
        )
    }
}

object StudentBoardRoute {
    const val postDetailARG = "postId"

    const val defaultRoute = "student_board"
    const val detailRoute = "student_board_detail/{$postDetailARG}"
    const val writeRoute = "student_board_write"

    fun detailRoute(postId: Long) = "student_board_detail/$postId"
}