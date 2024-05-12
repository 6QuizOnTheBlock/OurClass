package com.sixkids.student.relay.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sixkids.student.relay.create.RelayCreateRoute
import com.sixkids.student.relay.detail.RelayDetailRoute
import com.sixkids.student.relay.history.RelayRoute
import com.sixkids.student.relay.navigation.RelayRoute.RELAY_ID_NAME
import com.sixkids.ui.SnackbarToken

fun NavController.navigateStudentRelayHistory(navOptions: NavOptions) {
    navigate(RelayRoute.defaultRoute, navOptions)
}

fun NavController.navigateStudentRelayDetail(relayId: Long) {
    navigate(RelayRoute.detailRoute(relayId))
}

fun NavController.navigateStudentRelayCreate() {
    navigate(RelayRoute.createRoute)
}

fun NavController.navigateStudentRelayCreateResult(newRelayId: Long) {
    navigate(RelayRoute.createResultRoute(newRelayId))
}

fun NavController.navigateStudentRelayJoin() {
    navigate(RelayRoute.joinRoute)
}

fun NavGraphBuilder.studentRelayNavGraph(
    navigateRelayDetail: (Long) -> Unit,
    navigateCreateRelay: () -> Unit,
    navigateJoinRelay: () -> Unit,
    navigateCreateRelayResult: (Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
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

    composable(route = RelayRoute.detailRoute,
        arguments = listOf(
            navArgument(RELAY_ID_NAME) { type = NavType.LongType },

        ))
    {

        RelayDetailRoute(
            handleException = handleException
        )
    }

    composable(route = RelayRoute.createRoute)
    {
        RelayCreateRoute(
            navigateToRelayResult = navigateCreateRelayResult,
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar
        )
    }
}

object RelayRoute {
    const val RELAY_ID_NAME = "relayId"
    const val NEW_RELAY_ID_NAME = "newRelayId"

    const val defaultRoute = "relay-history"
    const val createRoute = "relay-create"
    const val detailRoute = "relay-detail?relayId={$RELAY_ID_NAME}"
    const val createResultRoute = "relay-create-result?newRelayId={$NEW_RELAY_ID_NAME}"
    const val joinRoute = "relay-join"

    fun detailRoute(relayId: Long) = "relay-detail?relayId=$relayId"
    fun createResultRoute(newRelayId: Long) = "relay-create-result?newRelayId=$newRelayId"
}