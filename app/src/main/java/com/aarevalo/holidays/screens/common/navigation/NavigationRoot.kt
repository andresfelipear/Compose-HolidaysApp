package com.aarevalo.holidays.screens.common.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aarevalo.holidays.screens.yearCalendar.YearScreenRoot
import com.aarevalo.holidays.screens.monthCalendar.MonthScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        NavHost(
            navController = navController,
            startDestination = HomeScreenDes
        ){
            composable<HomeScreenDes>{
                YearScreenRoot(
                )
            }

            composable<MonthScreenDestination>{
                MonthScreenRoot(
                )
            }
        }
    }
}

@Serializable
object HomeScreenDes

@Serializable
object MonthScreenDestination