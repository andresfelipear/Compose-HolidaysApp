package com.aarevalo.holidays.screens.common.navigation


interface NavigationRootAction {
    data object OnSelectedYearlyView : NavigationRootAction
    data object OnSelectedMonthlyView : NavigationRootAction
    data object OnSelectedWeeklyView : NavigationRootAction
}