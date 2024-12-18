package com.aarevalo.holidays.screens.common.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aarevalo.holidays.screens.main.HomeScreenRoot
import com.aarevalo.holidays.screens.main.HomeScreenViewModel
import com.aarevalo.holidays.screens.monthCalendar.MonthScreenRoot
import com.aarevalo.holidays.screens.monthCalendar.MonthScreenViewModel
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
                val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
                HomeScreenRoot(
                    navigateTo = {
                        navController.navigate(MonthScreenDestination)
                    },
                    viewModel = homeScreenViewModel
                )
            }

            composable<MonthScreenDestination>{
                val monthScreenViewModel: MonthScreenViewModel = hiltViewModel()
                MonthScreenRoot(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    viewModel = monthScreenViewModel
                )
            }
        }
    }
}

@Serializable
object HomeScreenDes

@Serializable
object MonthScreenDestination