package com.sixkids.feature.navigator

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import com.sixkids.designsystem.theme.Cream
import com.sixkids.home.navigation.homeNavGraph

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    // TODO viewmodel: MainViewModel,
    navigator: MainNavigator = rememberMainNavigator()
) {
    Scaffold(
        bottomBar = {
            BottomNav(
                modifier = Modifier,
                selectedTab = navigator.currentTab ?: MainNavigationTab.HOME,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        )
        {
            homeNavGraph(innerPadding)
        }
    }
}


@Composable
fun BottomNav(
    modifier: Modifier,
    itemClick: (MainNavigationTab) -> Unit = {},
    selectedTab: MainNavigationTab,
) {
    val selectedItem = rememberUpdatedState(newValue = selectedTab)

    Surface(
        modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = Cream,
        ) {
            MainNavigationTab.entries.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.iconId),
                            contentDescription = null,
                            tint = item.iconTint
                        )
                    },

                    label = {
                        Text(text = stringResource(id = item.labelId))
                    },
                    selected = selectedItem.value == item,
                    onClick = {
                        itemClick(item)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White
                    )
                )
            }
        }
    }
}