package com.aarevalo.holidays.screens.common.navigation

import MyTopAppBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.aarevalo.holidays.R
import com.aarevalo.holidays.navigation.Route
import com.aarevalo.holidays.navigation.ScreensNavigator
import com.aarevalo.holidays.screens.common.MyBottomTabsBar
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class DrawerItem(
    val route: Route,
    val icon: ImageVector,
    val title: Int
)

@Composable
fun NavigationRoot(
){
    val screenNavigator = remember {
        ScreensNavigator()
    }

    val currentBottomTab = screenNavigator.currentBottomTab.collectAsState()
    val currentRoute = screenNavigator.currentRoute.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val isHolidaysTab = screenNavigator.currentBottomTab.map{ route ->
        route != BottomTab.Main
    }.collectAsState(initial = false)

    val isRootRoute = screenNavigator.isRootRoute.collectAsState()

    val drawerItems = remember {
        listOf(
            DrawerItem(
                route = Route.Settings,
                icon = Icons.Rounded.Settings,
                title = R.string.menu_settings
            ),
            DrawerItem(
                route = Route.About,
                icon = Icons.Rounded.Info,
                title = R.string.menu_about
            )
        )
    }

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                drawerItems = drawerItems,
                onItemClick = { route ->
                    scope.launch {
                        drawerState.close()
                        when(route){
                            Route.Settings -> {
                                screenNavigator.toRoute(Route.Settings)
                            }
                            Route.About -> {
                                screenNavigator.toRoute(Route.About)
                            }
                            else -> Unit
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    currentRoute = currentRoute,
                    drawerState = drawerState,
                    isHolidaysTab = isHolidaysTab.value,
                    isRootRoute = isRootRoute.value,
                    onAction = { action ->
                        when(action){
                            is NavigationAction.OnSelectedYearlyView -> {
                                screenNavigator.toRoute(Route.YearTab)
                            }
                            is NavigationAction.OnSelectedMonthlyView -> {
                                screenNavigator.toRoute(Route.MonthTab)
                            }
                            is NavigationAction.OnSelectedWeeklyView -> {
                                screenNavigator.toRoute(Route.WeekTab)
                            }
                            is NavigationAction.OnNavigateBack -> {
                                screenNavigator.navigateBack()
                            }
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(modifier = Modifier) {
                    MyBottomTabsBar(
                        bottomTabs = BottomTab.BOTTOM_TABS,
                        currentBottomTab = currentBottomTab.value,
                        onTabClicked = { bottomTab ->
                            screenNavigator.toTab(bottomTab)
                        }
                    )
                }
            },
            content = { padding ->
                NavigationContent(
                    padding = padding,
                    screenNavigator = screenNavigator
                )
            }
        )
    }
}




