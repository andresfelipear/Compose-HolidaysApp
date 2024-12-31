package com.aarevalo.holidays.screens.common.calendar

import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.YearMonth

data class CalendarScreenState(
    val currentYear: Int,
    val currentMonth: YearMonth,
    val currentWeek: Week
)