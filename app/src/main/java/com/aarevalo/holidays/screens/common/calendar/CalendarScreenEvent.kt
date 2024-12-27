package com.aarevalo.holidays.screens.common.calendar

sealed class CalendarScreenEvent
{
    data object UpdatedYear : CalendarScreenEvent()
    data object UpdatedMonth : CalendarScreenEvent()

}