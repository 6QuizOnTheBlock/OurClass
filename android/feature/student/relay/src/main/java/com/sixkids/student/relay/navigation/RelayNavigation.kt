package com.sixkids.student.relay.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.student.relay.history.RelayRoute

fun NavController.navigateStudentRelayHistory(navOptions: NavOptions) {
    navigate(RelayRoute.defaultRoute, navOptions)
}

fun NavController.navigateStudentRelayDetail(relayId: Long, groupId: Long?) {
    navigate(RelayRoute.detailRoute(relayId, groupId))
}

fun NavController.navigateStudentRelayCreate() {
    navigate(RelayRoute.createRoute)
}

fun NavController.navigateStudentRelayJoin() {
    navigate(RelayRoute.joinRoute)
}

fun NavGraphBuilder.studentRelayNavGraph(
    navigateRelayDetail: (Long, Long?) -> Unit,
    navigateCreateRelay: () -> Unit,
    navigateJoinRelay: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
    onBackClick : () -> Unit
) {
    composable(route = RelayRoute.defaultRoute)
    {
        RelayRoute(
            navigateToDetail = { relayId, groupId ->
                navigateRelayDetail(relayId, groupId)
            },
            navigateToCreate = navigateCreateRelay,
            navigateToJoin = navigateJoinRelay,
            handleException = handleException
        )
    }
}

object RelayRoute {
    const val RELAY_ID_NAME = "relayId"
    const val RELAY_TITLE_NAME = "relayTitle"

    const val defaultRoute = "relay-history"
    const val createRoute = "relay-create"
    const val detailRoute = "relay-detail?challengeId={$RELAY_ID_NAME}&groupId={${RELAY_TITLE_NAME}}"
    const val joinRoute = "relay-join"

    fun detailRoute(relayId: Long, groupId: Long? = null) = "challenge-detail?challengeId=$relayId&groupId=$groupId"
}