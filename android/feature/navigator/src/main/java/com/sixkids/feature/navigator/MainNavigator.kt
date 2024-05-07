package com.sixkids.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sixkids.feature.signin.navigation.SignInRoute
import com.sixkids.feature.signin.navigation.navigateSignIn
import com.sixkids.feature.signin.navigation.navigateSignUp
import com.sixkids.feature.signin.navigation.navigateSignUpPhoto
import com.sixkids.teacher.board.navigation.navigateBoard
import com.sixkids.teacher.challenge.navigation.navigateChallengeCreatedResult
import com.sixkids.teacher.challenge.navigation.navigateChallengeDetail
import com.sixkids.teacher.challenge.navigation.navigateChallengeHistory
import com.sixkids.teacher.challenge.navigation.navigateCreateChallenge
import com.sixkids.teacher.home.navigation.HomeRoute
import com.sixkids.teacher.home.navigation.navigateHome
import com.sixkids.teacher.home.navigation.navigateRank
import com.sixkids.teacher.main.navigation.navigateNewOrganization
import com.sixkids.teacher.main.navigation.navigateProfile
import com.sixkids.teacher.main.navigation.navigateTeacherOrganizationList
import com.sixkids.teacher.manageclass.navigation.ManageClassRoute
import com.sixkids.teacher.manageclass.navigation.navigateManageClass
import com.sixkids.teacher.managestudent.navigation.ManageStudentRoute
import com.sixkids.teacher.managestudent.navigation.navigateManageStudent

class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = SignInRoute.defaultRoute
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val currentTab: MainNavigationTab?
        @Composable get() = currentDestination
            ?.route
            ?.let { MainNavigationTab.find(it) }

    fun navigate(tab: MainNavigationTab) {
        val navOptions = navOptions {
            popUpTo(HomeRoute.defaultRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainNavigationTab.HOME -> navController.navigateHome(navOptions)
            MainNavigationTab.BOARD -> navController.navigateBoard(navOptions)
            MainNavigationTab.MANAGE_STUDENT -> navController.navigateManageStudent(navOptions)
            MainNavigationTab.MANAGE_CLASS -> navController.navigateManageClass(navOptions)
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    /**
     * Home Navigation
     */
    fun navigateHome() {
        navController.navigate(HomeRoute.defaultRoute){
            popUpTo(navController.graph.id){
                inclusive = true
            }
        }
    }

    fun navigateRank(){
        navController.navigateRank()
    }

    /**
     * Manage Class Navigation
     */
    fun navigateManageClass(navOptions: NavOptions) {
        navController.navigate(ManageClassRoute.defaultRoute) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    /**
     * Manage Student Navigation
     */
    fun navigateManageStudent() {
        navController.navigateManageStudent(navOptions{
            popUpTo(ManageStudentRoute.defaultRoute){
                inclusive = true
            }
        })
    }

    /**
     * SignIn Navigation
     */
    fun navigateSignIn(){
        navController.navigateSignIn()
    }

    fun navigateSignUp(){
        navController.navigateSignUp()
    }

    fun navigateSignUpPhoto(isTeacher: Boolean){
        navController.navigateSignUpPhoto(isTeacher)
    }

    fun navigateChallengeHistory() {
        navController.navigateChallengeHistory()
    }

    fun navigateChallengeDetail(challengeId: Int) {
        navController.navigateChallengeDetail(challengeId)
    }

    fun navigateCreateChallenge() {
        navController.navigateCreateChallenge()
    }

    fun navigateChallengeCreatedResult(challengeId: Int, title: String) {
        navController.navigateChallengeCreatedResult(challengeId, title)
    }

    fun navigateTeacherOrganizationList(){
        navController.navigateTeacherOrganizationList()
    }

    fun navigateNewOrganization(){
        navController.navigateNewOrganization()
    }

    fun navigateProfile(){
        navController.navigateProfile()
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainNavigationTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
