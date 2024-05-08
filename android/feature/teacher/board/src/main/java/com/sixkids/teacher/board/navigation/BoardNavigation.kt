package com.sixkids.teacher.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.board.chatting.ChattingRoute
import com.sixkids.teacher.board.main.BoardMainRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateBoard(navOptions: NavOptions) {
    navigate(BoardRoute.defaultRoute, navOptions)
}

fun NavController.navigateChatting(){
    navigate(BoardRoute.chattingRoute)
}

fun NavGraphBuilder.boardNavGraph(
    padding: PaddingValues,
    onBackClick : () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    navigateToChatting : () -> Unit
) {
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
    const val chattingRoute = "chatting"
}