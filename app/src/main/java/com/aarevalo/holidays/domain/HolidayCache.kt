package com.aarevalo.holidays.domain

import com.aarevalo.holidays.domain.model.Holiday

interface HolidayCache {
    fun refreshHolidays(newHolidays: List<Holiday>)
    fun clearHolidays()
    fun getHolidays(): List<Holiday>?
}