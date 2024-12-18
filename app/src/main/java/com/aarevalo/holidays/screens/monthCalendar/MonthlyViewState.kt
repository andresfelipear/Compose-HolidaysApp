package com.aarevalo.holidays.screens.monthCalendar

import java.time.YearMonth

data class MonthlyViewState(
    val currentMonth: YearMonth = YearMonth.now(),
)
