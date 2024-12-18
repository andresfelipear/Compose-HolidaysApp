package com.aarevalo.holidays.screens.yearCalendar

interface YearScreenAction {
    data class UpdateYear(val increment: Boolean) : YearScreenAction
}