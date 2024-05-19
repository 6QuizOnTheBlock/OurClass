package com.sixkids.teacher.manageclass.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sixkids.teacher.manageclass.chattingfilter.ChattingFilterRoute
import com.sixkids.teacher.manageclass.invite.ClassInviteRoute
import com.sixkids.teacher.manageclass.main.ManageClassMainRoute
import com.sixkids.teacher.manageclass.setting.ClassSettingRoute
import com.sixkids.teacher.manageclass.statistics.StatisticsRoute
import com.sixkids.ui.SnackbarToken

fun NavController.navigateManageClass(navOptions: NavOptions) {
    navigate(ManageClassRoute.defaultRoute,navOptions)
}

fun NavController.navigateChattingFilter() {
    navigate(ManageClassRoute.chattingFilterRoute)
}

fun NavController.navigateInvite() {
    navigate(ManageClassRoute.inviteRoute)
}

fun NavController.navigateClassSetting() {
    navigate(ManageClassRoute.settingRoute)
}

fun NavController.navigateStatistics(){
    navigate(ManageClassRoute.summaryRoute)
}

fun NavGraphBuilder.manageClassNavGraph(
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit,
    navigateToClassSummary: () -> Unit,
    navigateToClassSetting: () -> Unit,
    navigateToChattingFilter: () -> Unit,
    navigateToInvite: () -> Unit,
    navigateBack: () -> Unit
) {
    composable(route = ManageClassRoute.defaultRoute){
        ManageClassMainRoute(
            padding = padding,
            navigateToClassSummary = navigateToClassSummary,
            navigateToClassSetting = navigateToClassSetting,
            navigateToChattingFilter = navigateToChattingFilter,
            navigateToInvite = navigateToInvite
        )
    }

    composable(route = ManageClassRoute.chattingFilterRoute){
        ChattingFilterRoute(
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(ManageClassRoute.inviteRoute){
        ClassInviteRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(ManageClassRoute.settingRoute){
        ClassSettingRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar,
            navigateBack = navigateBack
        )
    }

    composable(ManageClassRoute.summaryRoute){
        StatisticsRoute()
    }
}

object ManageClassRoute {
    const val defaultRoute = "manage_class"
    const val chattingFilterRoute = "manage_class/chatting_filter"
    const val summaryRoute = "manage_class/summary"
    const val settingRoute = "manage_class/setting"
    const val inviteRoute = "manage_class/invite"
}