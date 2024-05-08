package com.sixkids.teacher.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.board.chatting.ChattingRoute
import com.sixkids.teacher.board.main.BoardMainRoute
import com.sixkids.teacher.board.post.PostRoute
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

fun NavController.navigatePostDetail() {
    navigate(BoardRoute.postDetailRoute)
}

fun NavController.navigateChatting(){
    navigate(BoardRoute.chattingRoute)
}

fun NavGraphBuilder.boardNavGraph(
    padding: PaddingValues,
    navigateToPost: () -> Unit,
    onBackClick : () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    navigateToChatting : () -> Unit
) {
    composable(route = BoardRoute.defaultRoute){
        BoardMainRoute(
            padding = padding,
            navigateToPost = navigateToPost,
            navigateToChatting : () -> Unit
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
    
    composable(route = BoardRoute.defaultRoute){
        BoardMainRoute(padding, navigateToChatting)
    }

    composable(route = BoardRoute.chattingRoute){
         ChattingRoute(
             onBackClick = onBackClick,
             onShowSnackBar = onShowSnackBar
         )
    }
}

object BoardRoute {
    const val defaultRoute = "board"
    const val postRoute = "post"
    const val postWriteRoute = "post_write"
    const val postDetailRoute = "post_detail"
    const val chattingRoute = "chatting"
}