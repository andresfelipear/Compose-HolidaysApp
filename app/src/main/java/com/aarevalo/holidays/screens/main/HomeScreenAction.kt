package com.aarevalo.holidays.screens.main

interface HomeScreenAction {
    data class UpdateYear(val increment: Boolean) : HomeScreenAction
    data object OnSelectedYearlyView : HomeScreenAction
    data object OnSelectedMonthlyView : HomeScreenAction
    data object OnSelectedWeeklyView : HomeScreenAction
}