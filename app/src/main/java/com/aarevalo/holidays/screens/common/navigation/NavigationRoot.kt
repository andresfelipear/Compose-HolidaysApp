package com.aarevalo.holidays.screens.common.navigation

import MyTopAppBar
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aarevalo.holidays.R
import com.aarevalo.holidays.navigation.Route
import com.aarevalo.holidays.navigation.ScreensNavigator
import com.aarevalo.holidays.screens.common.MyBottomTabsBar
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.holidays.HolidaysScreenRoot
import com.aarevalo.holidays.screens.yearCalendar.YearScreenRoot
import com.aarevalo.holidays.screens.monthCalendar.MonthScreenRoot
import com.aarevalo.holidays.screens.weekCalendar.WeeklyCalendarScreenRoot
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val isNotHomeTab = screenNavigator.currentBottomTab.map{ route ->
        route != BottomTab.Main
    }.collectAsState(initial = false)

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
                        screenNavigator.toRoute(route)
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    drawerState = drawerState,
                    isNotHomeTab = isNotHomeTab.value,
                    onAction = { action ->
                        when(action){
                            is NavigationRootAction.OnSelectedYearlyView -> {
                                screenNavigator.toRoute(Route.YearTab)
                            }
                            is NavigationRootAction.OnSelectedMonthlyView -> {
                                screenNavigator.toRoute(Route.MonthTab)
                            }
                            is NavigationRootAction.OnSelectedWeeklyView -> {
                                screenNavigator.toRoute(Route.WeekTab)
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




