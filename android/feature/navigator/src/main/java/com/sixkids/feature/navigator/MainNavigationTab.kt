package com.sixkids.feature.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.Red
import com.sixkids.navigator.R
import com.sixkids.teacher.board.navigation.BoardRoute
import com.sixkids.teacher.home.navigation.HomeRoute


enum class MainNavigationTab(
    @DrawableRes val iconId: Int,
    val iconTint: Color,
    @StringRes val labelId: Int,
    val route: String,
) {
    HOME(
        iconId = R.drawable.home_rocket,
        iconTint = Red,
        labelId = R.string.bottom_navigation_tab_label_home,
        route = HomeRoute.defaultRoute,
    ),
    BOARD(
        iconId = R.drawable.board,
        iconTint = Blue,
        labelId = R.string.bottom_navigation_tab_label_board,
        route = BoardRoute.defaultRoute,
    ),
    MANAGE_STUDENT(
        iconId = R.drawable.manage_student,
        iconTint = Purple,
        labelId = R.string.bottom_navigation_tab_label_manage_student,
        route = "StatisticsRoute.route",
    ),
    MANAGE_CLASS(
        iconId = R.drawable.manage_class,
        iconTint = Green,
        labelId = R.string.bottom_navigation_tab_label_manage_class,
        route = "CommunityRoute.route",
    )
    ;

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainNavigationTab? {
            return entries.find { it.route == route }
        }
    }
}