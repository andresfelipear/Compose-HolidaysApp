package com.aarevalo.holidays.screens.monthCalendar

sealed  class MonthScreenEvent {
    data object UpdatedMonth : MonthScreenEvent()
}