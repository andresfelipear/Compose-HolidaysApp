package com.aarevalo.holidays.screens.monthCalendar

import com.aarevalo.holidays.screens.main.HomeScreenAction

interface MonthlyViewAction {
    data class UpdateMonth(val increment: Boolean) : HomeScreenAction
    data object OnSelectedYearView : HomeScreenAction
    data object OnSelectedWeeklyView : HomeScreenAction
}