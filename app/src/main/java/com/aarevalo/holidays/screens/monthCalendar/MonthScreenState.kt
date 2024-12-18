package com.aarevalo.holidays.screens.monthCalendar

import java.time.Month
import java.time.YearMonth

data class MonthScreenState(
    val currentMonth: Month = YearMonth.now().month,
    val currentYear: Int = YearMonth.now().year
)