package com.aarevalo.holidays.screens.yearCalendar

sealed class YearScreenEvent
{
    data object UpdatedYear : YearScreenEvent()
}