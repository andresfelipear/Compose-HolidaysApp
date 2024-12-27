package com.aarevalo.holidays.screens.main


interface HomeScreenAction {
    data object OnSelectedYearlyView : HomeScreenAction
    data object OnSelectedMonthlyView : HomeScreenAction
    data object OnSelectedWeeklyView : HomeScreenAction
}