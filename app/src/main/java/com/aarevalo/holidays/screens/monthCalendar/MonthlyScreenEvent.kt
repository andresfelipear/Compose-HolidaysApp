package com.aarevalo.holidays.screens.monthCalendar

import com.aarevalo.holidays.screens.main.HomeScreenEvent

sealed  class MonthlyScreenEvent {
    data object UpdatedMonth : MonthlyScreenEvent()
}