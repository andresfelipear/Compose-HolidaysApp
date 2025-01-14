package com.aarevalo.holidays.screens.common.navigation


interface NavigationAction {
    data object OnSelectedYearlyView : NavigationAction
    data object OnSelectedMonthlyView : NavigationAction
    data object OnSelectedWeeklyView : NavigationAction
    data object OnNavigateBack : NavigationAction
}