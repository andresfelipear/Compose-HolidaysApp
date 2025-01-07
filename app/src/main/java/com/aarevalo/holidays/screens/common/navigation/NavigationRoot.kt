package com.aarevalo.holidays.screens.common.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aarevalo.holidays.data.local.FakeHolidaysLocalDataSource.holidays
import com.aarevalo.holidays.navigation.Route
import com.aarevalo.holidays.navigation.ScreensNavigator
import com.aarevalo.holidays.screens.common.MyBottomTabsBar
import com.aarevalo.holidays.screens.common.MyTopAppBar
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.holidays.HolidaysScreen
import com.aarevalo.holidays.screens.holidays.HolidaysScreenRoot
import com.aarevalo.holidays.screens.yearCalendar.YearScreenRoot
import com.aarevalo.holidays.screens.monthCalendar.MonthScreenRoot
import com.aarevalo.holidays.screens.weekCalendar.WeeklyCalendarScreenRoot
import kotlinx.coroutines.flow.map


@Composable
fun NavigationRoot(
){
    val screenNavigator = remember {
        ScreensNavigator()
    }

    val currentBottomTab = screenNavigator.currentBottomTab.collectAsState()

    val isHolidaysTab = screenNavigator.currentRoute.map{ route ->
        route is Route.HolidaysTab
    }.collectAsState(initial = false)

    Scaffold(
        topBar = {
            MyTopAppBar(
                isHolidaysTab = isHolidaysTab.value,
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

@Composable
fun NavigationContent(
    padding: PaddingValues,
    screenNavigator: ScreensNavigator
){
    val parentNavController = rememberNavController()
    screenNavigator.setParentNavController(parentNavController)

    val viewModel: CalendarScreenViewModel = hiltViewModel()

    Surface(
        modifier = Modifier
            .padding(padding)
    ){
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = parentNavController,
            startDestination = Route.MainTab.routeName,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
        ){
            composable(Route.MainTab.routeName){
                val mainNestedNavController = rememberNavController()
                screenNavigator.setNestedNavController(mainNestedNavController)

                NavHost(
                    navController = mainNestedNavController,
                    startDestination = Route.YearTab.routeName
                ){
                    composable(route = Route.YearTab.routeName){
                        YearScreenRoot(viewModel = viewModel)
                    }
                    composable(route = Route.MonthTab.routeName){
                        MonthScreenRoot(viewModel = viewModel)
                    }
                    composable(route = Route.WeekTab.routeName){
                        WeeklyCalendarScreenRoot(viewModel = viewModel)
                    }
                }
            }

            composable(Route.HolidaysTab.routeName){
                HolidaysScreenRoot(viewModel = viewModel)
            }
        }
    }
}
