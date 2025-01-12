package com.aarevalo.holidays.screens.common.calendar

import com.aarevalo.holidays.domain.model.Country

interface CalendarScreenAction {
    data class UpdateYear(val increment: Boolean) : CalendarScreenAction

    data class UpdateMonth(val increment: Boolean) : CalendarScreenAction

    data class UpdateWeek(val increment: Boolean) : CalendarScreenAction

    data class UpdateCountry(val country: Country) : CalendarScreenAction
}