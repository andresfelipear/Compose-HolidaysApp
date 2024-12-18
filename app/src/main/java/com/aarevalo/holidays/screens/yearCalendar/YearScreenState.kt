package com.aarevalo.holidays.screens.yearCalendar

import com.aarevalo.holidays.calendar.domain.model.MonthCalendar
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

data class YearScreenState(
    val currentYear: Int = LocalDate.now().year,
    val months: List<MonthCalendar> = Month.entries.map { month ->
        MonthCalendar(
            month = YearMonth.of(currentYear, month)
        )
    },
    val currentMonth: YearMonth = YearMonth.now(),
)