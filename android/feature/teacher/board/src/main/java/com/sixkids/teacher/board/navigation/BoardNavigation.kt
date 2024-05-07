package com.sixkids.teacher.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.board.main.BoardMainRoute
import com.sixkids.teacher.board.post.PostRoute
import com.sixkids.teacher.board.postdetail.PostDetailRoute
import com.sixkids.teacher.board.postwrite.PostWriteRoute

fun NavController.navigateBoard(navOptions: NavOptions) {
    navigate(BoardRoute.defaultRoute, navOptions)
}

fun NavController.navigatePost() {
    navigate(BoardRoute.postRoute)
}

fun NavController.navigatePostWrite() {
    navigate(BoardRoute.postWriteRoute)
}

fun NavController.navigatePostDetail() {
    navigate(BoardRoute.postDetailRoute)
}

fun NavGraphBuilder.boardNavGraph(
    padding: PaddingValues,
    navigateToPost: () -> Unit,
) {
    composable(route = BoardRoute.defaultRoute){
        BoardMainRoute(
            padding = padding,
            navigateToPost = navigateToPost
        )
    }

    composable(BoardRoute.postRoute){
        PostRoute(padding)
    }

    composable(BoardRoute.postWriteRoute){
        PostWriteRoute(padding)
    }

    composable(BoardRoute.postDetailRoute){
        PostDetailRoute(
            padding = padding
        )
    }
}

object BoardRoute {
    const val defaultRoute = "board"
    const val postRoute = "post"
    const val postWriteRoute = "post_write"
    const val postDetailRoute = "post_detail"
}