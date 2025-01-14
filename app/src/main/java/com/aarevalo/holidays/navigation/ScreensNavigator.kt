package com.aarevalo.holidays.navigation

import android.util.Log
import androidx.navigation.NavController
import com.aarevalo.holidays.screens.common.navigation.BottomTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ScreensNavigator {

    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    private lateinit var parentNavController: NavController
    private lateinit var nestedNavController: NavController

    private var nestedNavControllerObserverJob: Job? = null
    private var parentNavControllerObserverJob: Job? = null

    val currentBottomTab = MutableStateFlow<BottomTab?>(null)
    val currentRoute = MutableStateFlow<Route?>(null)
    val isRootRoute = MutableStateFlow(false)

    fun setParentNavController(navController: NavController) {
        parentNavController = navController
        parentNavControllerObserverJob?.cancel()
        parentNavControllerObserverJob = scope.launch {
            navController.currentBackStackEntryFlow.map { backStackEntry ->
                val bottomTab = when(val routeName = backStackEntry.destination.route) {
                    Route.MainTab.navCommand -> BottomTab.Main
                    Route.HolidaysTab.navCommand -> BottomTab.Holidays
                    Route.Settings.navCommand -> null
                    Route.About.navCommand -> null
                    null -> null
                    else -> throw RuntimeException("unsupported bottom tab: $routeName")
                }

                val route = when(val routeName = backStackEntry.destination.route) {
                    Route.MainTab.navCommand -> Route.MainTab
                    Route.HolidaysTab.navCommand -> Route.HolidaysTab
                    Route.Settings.navCommand -> Route.Settings
                    Route.About.navCommand -> Route.About
                    null -> null
                    else -> throw RuntimeException("unsupported route $routeName")
                }
                Pair(route, bottomTab)
            }.collect{ (route, bottomTab) ->
                currentBottomTab.value = bottomTab
                currentRoute.value = route
                isRootRoute.value = route != Route.About && route != Route.Settings
            }
        }
    }

    fun setNestedNavController(navController: NavController) {
        nestedNavController = navController
        nestedNavControllerObserverJob?.cancel()

        nestedNavControllerObserverJob = scope.launch {
            navController.currentBackStackEntryFlow.map { backStackEntry ->
                val route = when(val routeName = backStackEntry.destination.route) {
                    Route.MonthTab.navCommand -> Route.MonthTab
                    Route.YearTab.navCommand -> Route.YearTab
                    Route.WeekTab.navCommand -> Route.WeekTab
                    Route.Holidays.navCommand -> Route.Holidays
                    null -> null
                    else -> throw RuntimeException("unsupported route $routeName")

            }
                Pair(backStackEntry, route)
            }.collect{ (backStackEntry, route) ->
                currentRoute.value = route
                isRootRoute.value = route != Route.About && route != Route.Settings
            }
        }
    }

    fun toTab(bottomTab: BottomTab) {

        val route = when(bottomTab) {
            BottomTab.Main -> Route.MainTab
            BottomTab.Holidays -> Route.HolidaysTab
        }

        val destinationExists = parentNavController.graph.findNode(route.routeName) != null

        if (destinationExists) {
            parentNavController.navigate(route.routeName){
                parentNavController.graph.startDestinationRoute?.let { startRoute ->
                    popUpTo(startRoute) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }else {
            Log.e("Navigation", "Destination route does not exist: ${route.routeName}")
        }
    }

    fun toRoute(route: Route){
        when(route){
            is Route.Settings, is Route.About -> {
                navigateToDestination(route.navCommand)
            }
            else -> nestedNavController.navigate(route.navCommand)
        }
    }

    fun navigateBack()
    {
        parentNavController.navigateUp()
    }

    private fun navigateToDestination(route: String){
        val destinationExists = parentNavController.graph.findNode(route) != null

        if (destinationExists) {
            parentNavController.navigate(route) {
                parentNavController.graph.startDestinationRoute?.let { startRoute ->
                    popUpTo(startRoute) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        } else{
            Log.e("Navigation", "Destination route does not exist: $route")
        }
    }
}
