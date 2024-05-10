package com.sixkids.feature.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.Green
import com.sixkids.designsystem.theme.Purple
import com.sixkids.designsystem.theme.Red
import com.sixkids.navigator.R
import com.sixkids.student.board.navigation.StudentHomeRoute
import com.sixkids.student.navigation.ChallengeRoute
import com.sixkids.teacher.board.navigation.BoardRoute
import com.sixkids.teacher.home.navigation.HomeRoute
import com.sixkids.teacher.manageclass.navigation.ManageClassRoute
import com.sixkids.teacher.managestudent.navigation.ManageStudentRoute


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
        route = ManageStudentRoute.defaultRoute
    ),
    MANAGE_CLASS(
        iconId = R.drawable.manage_class,
        iconTint = Green,
        labelId = R.string.bottom_navigation_tab_label_manage_class,
        route = ManageClassRoute.defaultRoute,
    ),
    STUDENT_HOME(
        iconId = R.drawable.home,
        iconTint = Red,
        labelId = R.string.bottom_navigation_tab_label_home,
        route = StudentHomeRoute.defaultRoute,
    ),
    STUDENT_BOARD(
        iconId = R.drawable.board,
        iconTint = Blue,
        labelId = R.string.bottom_navigation_tab_label_board,
        route = "",
    ),
    STUDENT_RELAY(
        iconId = R.drawable.folded_hands,
        iconTint = Purple,
        labelId = R.string.bottom_navigation_tab_label_relay,
        route = "",
    ),
    STUDENT_CHALLENGE(
        iconId = R.drawable.home_rocket,
        iconTint = Green,
        labelId = R.string.bottom_navigation_tab_label_challenge,
        route = ChallengeRoute.defaultRoute,
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
