package com.aarevalo.holidays.domain.model

import java.time.YearMonth

data class MonthCalendar(
    val month: YearMonth,
    val isMonthlyView: Boolean = true,
)