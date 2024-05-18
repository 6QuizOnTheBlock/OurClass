package com.sixkids.student.relay.navigation

import androidx.compose.foundation.layout.PaddingValues
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
import com.sixkids.student.relay.pass.answer.RelayAnswerRoute
import com.sixkids.student.relay.pass.tagging.receiver.RelayTaggingReceiverRoute
import com.sixkids.student.relay.pass.tagging.sender.RelayTaggingSenderRoute
import com.sixkids.student.relay.result.RelayCreateResultRoute
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

fun NavController.navigateStudentRelayCreateResult() {
    navigate(RelayRoute.createResultRoute)
}

fun NavController.navigateStudentRelayJoin() {
    navigate(RelayRoute.joinRoute)
}

fun NavController.navigateStudentRelayAnswer(relayId: Long) {
    navigate(RelayRoute.answerRoute(relayId))
}

fun NavController.navigateStudentRelayTaggingSender(relayId: Long, question: String) {
    navigate(RelayRoute.taggingSenderRoute(relayId, question))
}

fun NavController.navigateStudentRelayTaggingReceiver(relayId: Long) {
    navigate(RelayRoute.taggingReceiverRoute(relayId))
}

fun NavGraphBuilder.studentRelayNavGraph(
    padding: PaddingValues,
    navigateRelayHistory: () -> Unit,
    navigateRelayDetail: (Long) -> Unit,
    navigateCreateRelay: () -> Unit,
    navigateJoinRelay: () -> Unit,
    navigateCreateRelayResult: () -> Unit,
    navigateAnswerRelay: (Long) -> Unit,
    navigateTaggingSender: (Long, String) -> Unit,
    navigateTaggingReceiver: (Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    composable(route = RelayRoute.defaultRoute)
    {
        RelayRoute(
            padding = padding,
            navigateToDetail = { relayId ->
                navigateRelayDetail(relayId)
            },
            navigateToCreate = navigateCreateRelay,
            navigateToAnswer = navigateAnswerRelay,
            navigateToTaggingReceiver = navigateTaggingReceiver,
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

    composable(route = RelayRoute.createResultRoute)
    {
        RelayCreateResultRoute(
            navigateToRelayHistory = navigateRelayHistory,
            handleException = handleException
        )
    }

    composable(route = RelayRoute.answerRoute,
        arguments = listOf(
            navArgument(RELAY_ID_NAME) { type = NavType.LongType },

        ))
    {
        RelayAnswerRoute(
            navigateToTaggingSenderRelay = navigateTaggingSender,
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = RelayRoute.taggingSenderRoute,
        arguments = listOf(
            navArgument(RELAY_ID_NAME) { type = NavType.LongType },
            navArgument(RelayRoute.RELAY_QUESTION_NAME) { type = NavType.StringType }
        ))
    {
        RelayTaggingSenderRoute(
            navigateToRelayHistory = navigateRelayHistory,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = RelayRoute.taggingReceiverRoute,
        arguments = listOf(
            navArgument(RELAY_ID_NAME) { type = NavType.LongType }
        ))
    {
        RelayTaggingReceiverRoute(
            navigateToRelayHistory = navigateRelayHistory,
        )
    }
}

object RelayRoute {
    const val RELAY_ID_NAME = "relayId"
    const val RELAY_QUESTION_NAME = "question"

    const val defaultRoute = "student/relay-history"
    const val createRoute = "relay-create"
    const val detailRoute = "relay-detail?relayId={$RELAY_ID_NAME}"
    const val answerRoute = "relay-answer?relayId={$RELAY_ID_NAME}"
    const val taggingSenderRoute = "relay-tagging-sender?relayId={$RELAY_ID_NAME}&question={$RELAY_QUESTION_NAME}"
    const val taggingReceiverRoute = "relay-tagging-receiver?relayId={$RELAY_ID_NAME}"
    const val createResultRoute = "relay-create-result"
    const val joinRoute = "relay-join"

    fun detailRoute(relayId: Long) = "relay-detail?relayId=$relayId"
    fun answerRoute(relayId: Long) = "relay-answer?relayId=$relayId"
    fun taggingSenderRoute(relayId: Long, question: String) = "relay-tagging-sender?relayId=$relayId&question=$question"
    fun taggingReceiverRoute(relayId: Long) = "relay-tagging-receiver?relayId=$relayId"
}