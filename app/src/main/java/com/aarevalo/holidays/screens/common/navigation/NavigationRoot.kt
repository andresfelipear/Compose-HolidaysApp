package com.aarevalo.holidays.screens.common.navigation

import com.aarevalo.holidays.screens.common.MyTopAppBar
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.aarevalo.holidays.navigation.Route
import com.aarevalo.holidays.navigation.ScreensNavigator
import com.aarevalo.holidays.screens.common.MyBottomTabsBar
import com.aarevalo.holidays.screens.common.calendar.CalendarScreenViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun NavigationRoot(
    viewModel: CalendarScreenViewModel = hiltViewModel(),
){
    val screenNavigator = remember {
        ScreensNavigator()
    }

    val currentBottomTab = screenNavigator.currentBottomTab.collectAsState()
    val currentRoute = screenNavigator.currentRoute.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val isHolidaysTab = screenNavigator.currentBottomTab.map{ route ->
        route != BottomTab.Main
    }.collectAsState(initial = false)

    val shouldShowBottomBar = screenNavigator.currentRoute.map{ route ->
        route != Route.Settings && route != Route.About
    }.collectAsState(initial = true)

    val isRootRoute = screenNavigator.isRootRoute.collectAsState()
    val drawerItems = viewModel.drawerItems.collectAsState()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scope.launch {
                viewModel.getCurrentLocation()
            }
        }
    }

    val locationPermissionState = remember {
        mutableStateOf(ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    LaunchedEffect(locationPermissionState.value) {
        if (!locationPermissionState.value) {
            locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            scope.launch {
                viewModel.getCurrentLocation()
            }
        }
    }

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                drawerItems = drawerItems.value,
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
                if(shouldShowBottomBar.value){
                    BottomAppBar(modifier = Modifier) {
                        MyBottomTabsBar(
                            bottomTabs = BottomTab.BOTTOM_TABS,
                            currentBottomTab = currentBottomTab.value,
                            onTabClicked = { bottomTab ->
                                screenNavigator.toTab(bottomTab)
                            }
                        )
                    }
                }
            },
            content = { padding ->
                NavigationContent(
                    padding = padding,
                    screenNavigator = screenNavigator,
                    viewModel = viewModel
                )
            }
        )
    }
}




