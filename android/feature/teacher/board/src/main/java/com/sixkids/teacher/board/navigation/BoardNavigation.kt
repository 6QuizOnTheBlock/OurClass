package com.sixkids.teacher.board.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.board.main.BoardMainRoute

fun NavController.navigateBoard(navOptions: NavOptions) {
    navigate(BoardRoute.defaultRoute, navOptions)
}

fun NavGraphBuilder.boardNavGraph(
    padding: PaddingValues,
) {
    composable(route = BoardRoute.defaultRoute){
        BoardMainRoute(padding)
    }
}

object BoardRoute {
    const val defaultRoute = "board"
}