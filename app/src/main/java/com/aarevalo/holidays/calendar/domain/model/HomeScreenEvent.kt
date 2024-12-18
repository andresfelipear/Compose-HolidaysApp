package com.aarevalo.holidays.calendar.domain.model

sealed class HomeScreenEvent
{
    data object UpdatedYear : HomeScreenEvent()
    data object UpdatedMonth : HomeScreenEvent()
    data object SelectedYearView : HomeScreenEvent()
    data object SelectedMonthView : HomeScreenEvent()
    data object SelectedWeeklyView : HomeScreenEvent()
}