package com.sixkids.teacher.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.teacher.board.chatting.ChattingRoute
import com.sixkids.teacher.board.main.BoardMainRoute
import com.sixkids.teacher.board.postlist.PostRoute
import com.sixkids.teacher.board.postdetail.PostDetailRoute
import com.sixkids.teacher.board.postwrite.PostWriteRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateBoard(navOptions: NavOptions) {
    navigate(BoardRoute.defaultRoute, navOptions)
}

fun NavController.navigatePost() {
    navigate(BoardRoute.postRoute)
}

fun NavController.navigatePostWrite() {
    navigate(BoardRoute.postWriteRoute)
}

fun NavController.navigatePostDetail(postId: Long) {
    navigate(BoardRoute.postDetailRoute(postId))
}

fun NavController.navigateChatting() {
    navigate(BoardRoute.chattingRoute)
}

fun NavGraphBuilder.boardNavGraph(
    padding: PaddingValues,
    navigateToPost: () -> Unit,
    navigateToPostDetail: (Long) -> Unit,
    navigateToPostWrite: () -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    navigateToChatting: () -> Unit
) {
    composable(route = BoardRoute.defaultRoute) {
        BoardMainRoute(
            padding = padding,
            navigateToPost = navigateToPost,
            navigateToChatting = navigateToChatting,
        )
    }

    composable(
        route = BoardRoute.postRoute,
    ) {
        PostRoute(
            padding = padding,
            navigateToDetail = navigateToPostDetail,
            navigateToWrite = navigateToPostWrite,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(BoardRoute.postWriteRoute) {
        PostWriteRoute(
            padding = padding,
            navigateBack = onBackClick,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(
        route = BoardRoute.postDetailRoute,
        arguments = listOf(navArgument(BoardRoute.postDetailARG) { type = NavType.LongType })
    ) {
        PostDetailRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = BoardRoute.defaultRoute) {
        BoardMainRoute(
            padding = padding,
            navigateToPost = navigateToPost,
            navigateToChatting = navigateToChatting
        )
    }

    composable(route = BoardRoute.chattingRoute) {
        ChattingRoute(
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar
        )
    }
}

object BoardRoute {
    const val postDetailARG = "postId"

    const val defaultRoute = "board"
    const val postRoute = "post"
    const val postWriteRoute = "post_write"
    const val postDetailRoute = "post_detail/{$postDetailARG}"
    const val chattingRoute = "chatting"

    fun postDetailRoute(postId: Long) = "post_detail/$postId"
}
