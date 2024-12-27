package com.aarevalo.holidays.screens.common.calendar

import com.aarevalo.holidays.calendar.domain.model.MonthCalendar
import java.time.Month
import java.time.YearMonth

data class CalendarScreenState(
    val currentYear: Int,
    val currentMonth: YearMonth,
    val months: List<MonthCalendar> = Month.entries.map { month ->
        MonthCalendar(
            month = YearMonth.of(currentYear, month)
        )
    },

)