package com.aarevalo.holidays.calendar.domain.model

import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

data class HomeDataState(
    val currentYear: Int = LocalDate.now().year,
    val months: List<MonthCalendar> = Month.entries.map { month ->
        MonthCalendar(
            month = YearMonth.of(currentYear, month)
        )
    },
    val currentMonth: YearMonth = YearMonth.now(),
    val isLoading: Boolean = false,
    val error: String? = null



)