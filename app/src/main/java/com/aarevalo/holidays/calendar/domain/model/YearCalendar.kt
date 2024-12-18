package com.aarevalo.holidays.calendar.domain.model

data class YearCalendar(
    val year: Int,
    val months: List<MonthCalendar>
)
