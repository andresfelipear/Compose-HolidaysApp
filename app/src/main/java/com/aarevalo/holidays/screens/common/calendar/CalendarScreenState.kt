package com.aarevalo.holidays.screens.common.calendar

import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.State
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.YearMonth

data class CalendarScreenState(
    val currentYear: Int,
    val currentMonth: YearMonth,
    val currentWeek: Week,
    val country: Country? = null,
    val state: State? = null
)