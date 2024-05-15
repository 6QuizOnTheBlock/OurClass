package com.sixkids.teacher.relay.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.teacher.relay.detail.RelayDetailRoute
import com.sixkids.teacher.relay.history.RelayHistoryRoute
import com.sixkids.teacher.relay.navigation.RelayRoute.RELAY_ID_NAME

fun NavController.navigateTeacherRelayHistory() {
    navigate(RelayRoute.defaultRoute)
}

fun NavController.navigateTeacherRelayDetail(relayId: Long) {
    navigate(RelayRoute.detailRoute(relayId))
}

fun NavGraphBuilder.teacherRelayNavGraph(
    padding: PaddingValues,
    navigateRelayHistory: () -> Unit,
    navigateRelayDetail: (Long) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = RelayRoute.defaultRoute)
    {
        RelayHistoryRoute(
            padding = padding,
            navigateToDetail = { relayId ->
                navigateRelayDetail(relayId)
            },
            handleException = handleException
        )
    }

    composable(route = RelayRoute.detailRoute,
        arguments = listOf(
            navArgument(RELAY_ID_NAME) { type = NavType.LongType },

            ))
    {

        RelayDetailRoute(
            handleException = handleException
        )
    }

}

object RelayRoute {
    const val RELAY_ID_NAME = "relayId"

    const val defaultRoute = "relay-history"
    const val detailRoute = "relay-detail?relayId={$RELAY_ID_NAME}"

    fun detailRoute(relayId: Long) = "relay-detail?relayId=$relayId"
}