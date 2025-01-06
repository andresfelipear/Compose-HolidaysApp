package com.aarevalo.holidays.calendar.data

import com.aarevalo.holidays.calendar.domain.model.Holiday
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

object FakeHolidaysLocalDataSource {

    val holidays = mutableListOf<Holiday>()

    init {
        holidays.addAll(generateFakeHolidays())
    }

    private fun generateFakeHolidays(): List<Holiday> {
        val holidays = mutableListOf<Holiday>()
        val holidayNames = listOf("New Year", "Valentine's Day", "Easter", "Independence Day", "Halloween", "Christmas")
        val random = Random(System.currentTimeMillis())

        for (month in 1..12) {
            val numHolidays = random.nextInt(1, 5) // Generate between 1 and 4 holidays per month
            for (i in 1..numHolidays) {
                val day = random.nextInt(1, 27) + 1 // Days between 1 and 28 to avoid issues with February
                val name = holidayNames[random.nextInt(holidayNames.size)]
                val date = LocalDate.of(2025, month, day)
                holidays.add(Holiday(name, date))
            }
        }
        return holidays
    }
}