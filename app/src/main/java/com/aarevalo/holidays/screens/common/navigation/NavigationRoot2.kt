package com.aarevalo.holidays.screens.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aarevalo.holidays.common.navigation.Route
import com.aarevalo.holidays.common.navigation.ScreensNavigator

@Composable
fun NavigationRoot2(
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
            startDestination = Route.MainTab.routeName
        ){
            composable(Route.MainTab.routeName){
                val mainNestedNavController = rememberNavController()
                screenNavigator.setNestedNavController(mainNestedNavController)

                NavHost(
                    navController = mainNestedNavController,
                    startDestination = Route.YearTab.routeName
                ){
                    composable(route = Route.YearTab.routeName){

                    }
                }

            }
        }
    }
}
