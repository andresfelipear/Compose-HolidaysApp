package com.aarevalo.holidays.domain.model

data class YearCalendar(
    val year: Int,
    val months: List<com.aarevalo.holidays.domain.model.MonthCalendar>
)
