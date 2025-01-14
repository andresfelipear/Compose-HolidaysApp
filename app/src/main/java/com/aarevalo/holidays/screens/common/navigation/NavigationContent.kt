package com.aarevalo.holidays.screens.common.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aarevalo.holidays.navigation.Route
import com.aarevalo.holidays.navigation.ScreensNavigator
import com.aarevalo.holidays.screens.about.AboutScreen
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import com.aarevalo.holidays.screens.holidays.HolidaysScreenRoot
import com.aarevalo.holidays.screens.monthCalendar.MonthScreenRoot
import com.aarevalo.holidays.screens.settings.SettingsScreenRoot
import com.aarevalo.holidays.screens.weekCalendar.WeeklyCalendarScreenRoot
import com.aarevalo.holidays.screens.yearCalendar.YearScreenRoot

@Composable
fun NavigationContent(
    padding: PaddingValues,
    screenNavigator: ScreensNavigator,
    viewModel: CalendarScreenViewModel
){
    val parentNavController = rememberNavController()
    screenNavigator.setParentNavController(parentNavController)

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
                    startDestination = Route.YearTab.routeName,
                    enterTransition = { fadeIn(animationSpec = tween(200)) },
                    exitTransition = { fadeOut(animationSpec = tween(200)) },
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
                    composable(Route.Settings.routeName){
                        SettingsScreenRoot(viewModel = viewModel)
                    }
                    composable(Route.About.routeName){
                        AboutScreen()
                    }
                }
            }

            composable(Route.HolidaysTab.routeName){
                val holidaysNestedNavController = rememberNavController()
                screenNavigator.setNestedNavController(holidaysNestedNavController)

                NavHost(
                    navController = holidaysNestedNavController,
                    startDestination = Route.Holidays.routeName,
                    enterTransition = { fadeIn(animationSpec = tween(200)) },
                    exitTransition = { fadeOut(animationSpec = tween(200)) },
                ){
                    composable(route = Route.Holidays.routeName){
                        HolidaysScreenRoot(viewModel = viewModel)
                    }
                    composable(Route.Settings.routeName){
                        SettingsScreenRoot(viewModel = viewModel)
                    }
                    composable(Route.About.routeName){
                        AboutScreen()
                    }
                }
            }

            composable(Route.Settings.routeName){
                SettingsScreenRoot(viewModel = viewModel)
            }

            composable(Route.About.routeName){
                AboutScreen()
            }
        }
    }
}