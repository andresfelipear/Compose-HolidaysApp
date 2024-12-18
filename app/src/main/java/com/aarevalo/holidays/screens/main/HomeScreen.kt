package com.aarevalo.holidays.screens.main

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aarevalo.holidays.common.navigation.Route
import com.aarevalo.holidays.common.navigation.ScreensNavigator
import com.aarevalo.holidays.screens.common.BottomTab
import com.aarevalo.holidays.screens.common.MyBottomTabsBar
import com.aarevalo.holidays.screens.common.MyTopAppBar
import com.aarevalo.holidays.screens.holidays.HolidaysScreen
import com.aarevalo.holidays.screens.yearCalendar.YearScreenRoot
import com.aarevalo.holidays.screens.monthCalendar.MonthScreenRoot
import kotlinx.coroutines.flow.map


@Composable
fun HomeScreenRoot(
){
    val mainViewModel : HomeScreenViewModel = hiltViewModel()
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
                        is HomeScreenAction.OnSelectedYearlyView -> {
                            screenNavigator.toRoute(Route.YearTab)
                        }
                        is HomeScreenAction.OnSelectedMonthlyView -> {
                            screenNavigator.toRoute(Route.MonthTab)
                        }
                        is HomeScreenAction.OnSelectedWeeklyView -> {
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
            HomeScreen(
                padding = padding,
                screenNavigator = screenNavigator
            )
        }
    )
}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    screenNavigator: ScreensNavigator
){

    val parentNavController = rememberNavController()
    screenNavigator.setParentNavController(parentNavController)

    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp)
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
                        YearScreenRoot()
                    }
                    composable(route = Route.MonthTab.routeName){
                        MonthScreenRoot()
                    }
                }
            }

            composable(Route.HolidaysTab.routeName){
                HolidaysScreen()
            }
        }
    }
}
