package com.aarevalo.holidays.domain.model

import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

object WeekDateGenerator {
    fun generateWeekDates(yearMonth: YearMonth, weekNumber: Int): List<LocalDate> {
        val firstDayOfMonth = yearMonth.atDay(1)
        val weekFields = WeekFields.of(Locale.getDefault())

        val firstDayOfWeek = firstDayOfMonth
            .with(weekFields.weekOfMonth(), weekNumber.toLong())
            .with(weekFields.dayOfWeek(), 1)

        return (0..6).map { firstDayOfWeek.plusDays(it.toLong()) }
    }

    fun getCurrentWeekDates(yearMonth: YearMonth): List<LocalDate> {
        val today = LocalDate.now()
        val weekFields = WeekFields.of(Locale.getDefault())
        val currentWeek = today.get(weekFields.weekOfMonth())
        return generateWeekDates(yearMonth, currentWeek)
    }
}