package com.sixkids.student.relay.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.student.relay.detail.RelayDetailRoute
import com.sixkids.student.relay.history.RelayRoute

fun NavController.navigateStudentRelayHistory(navOptions: NavOptions) {
    navigate(RelayRoute.defaultRoute, navOptions)
}

fun NavController.navigateStudentRelayDetail(relayId: Long) {
    navigate(RelayRoute.detailRoute(relayId))
}

fun NavController.navigateStudentRelayCreate() {
    navigate(RelayRoute.createRoute)
}

fun NavController.navigateStudentRelayJoin() {
    navigate(RelayRoute.joinRoute)
}

fun NavGraphBuilder.studentRelayNavGraph(
    navigateRelayDetail: (Long) -> Unit,
    navigateCreateRelay: () -> Unit,
    navigateJoinRelay: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
    onBackClick : () -> Unit
) {
    composable(route = RelayRoute.defaultRoute)
    {
        RelayRoute(
            navigateToDetail = { relayId ->
                navigateRelayDetail(relayId)
            },
            navigateToCreate = navigateCreateRelay,
            navigateToJoin = navigateJoinRelay,
            handleException = handleException
        )
    }

    composable(route = RelayRoute.detailRoute)
    {
        RelayDetailRoute(
            handleException = handleException
        )
    }
}

object RelayRoute {
    const val RELAY_ID_NAME = "relayId"

    const val defaultRoute = "relay-history"
    const val createRoute = "relay-create"
    const val detailRoute = "relay-detail?relayId={$RELAY_ID_NAME}"
    const val joinRoute = "relay-join"

    fun detailRoute(relayId: Long) = "relay-detail?relayId=$relayId"
}