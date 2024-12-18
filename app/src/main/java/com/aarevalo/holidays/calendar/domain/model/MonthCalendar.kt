package com.aarevalo.holidays.calendar.domain.model

import java.time.YearMonth

data class MonthCalendar(
    val month: YearMonth,
    val isMonthlyView: Boolean = true,
)