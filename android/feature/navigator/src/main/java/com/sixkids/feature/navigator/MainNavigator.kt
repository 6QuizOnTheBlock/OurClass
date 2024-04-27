package com.sixkids.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sixkids.home.navigation.HomeRoute
import com.sixkids.home.navigation.navigateHome

class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = HomeRoute.defaultRoute
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val curretTab: MainNavigationTab?
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
            MainNavigationTab.BOARD -> TODO()
            MainNavigationTab.MANAGE_STUDENT -> TODO()
            MainNavigationTab.MANAGE_CLASS -> TODO()
        }
    }


    fun navigateHome(navOptions: NavOptions) {
        navController.navigate(HomeRoute.defaultRoute){
            popUpTo(navController.graph.id){
                inclusive = true
            }
        }
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