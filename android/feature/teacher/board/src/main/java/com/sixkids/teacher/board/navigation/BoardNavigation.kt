package com.sixkids.teacher.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.board.main.BoardMainRoute
import com.sixkids.teacher.board.post.PostRoute

fun NavController.navigateBoard(navOptions: NavOptions) {
    navigate(BoardRoute.defaultRoute, navOptions)
}

fun NavController.navigatePost() {
    navigate(BoardRoute.postRoute)
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
}

object BoardRoute {
    const val defaultRoute = "board"
    const val postRoute = "post"
}