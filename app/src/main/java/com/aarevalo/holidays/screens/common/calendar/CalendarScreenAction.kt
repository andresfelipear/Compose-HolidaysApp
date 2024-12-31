package com.aarevalo.holidays.screens.common.calendar

interface CalendarScreenAction {
    data class UpdateYear(val increment: Boolean) : CalendarScreenAction

    data class UpdateMonth(val increment: Boolean) : CalendarScreenAction

    data class UpdateWeek(val increment: Boolean) : CalendarScreenAction
}