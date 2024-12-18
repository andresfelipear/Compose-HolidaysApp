package com.aarevalo.holidays.calendar.domain.model

import java.time.Month

interface HomeScreenAction {
    data class UpdateYear(val increment: Boolean) : HomeScreenAction
    data class UpdateMonth(val increment: Boolean) : HomeScreenAction
    data object OnSelectedYearView : HomeScreenAction
    data object OnSelectedMonthView : HomeScreenAction
    data object OnSelectedWeeklyView : HomeScreenAction
}