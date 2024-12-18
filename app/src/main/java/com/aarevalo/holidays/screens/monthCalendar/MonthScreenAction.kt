package com.aarevalo.holidays.screens.monthCalendar

interface MonthScreenAction {
    data class UpdateMonth(val increment: Boolean) : MonthScreenAction
    data object OnSelectedYearView : MonthScreenAction
    data object OnSelectedWeeklyView : MonthScreenAction
}