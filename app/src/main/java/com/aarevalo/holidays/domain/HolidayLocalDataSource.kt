package com.aarevalo.holidays.domain

import com.aarevalo.holidays.domain.model.Holiday
import kotlinx.coroutines.flow.Flow

interface HolidayLocalDataSource {
    val taskFlow: Flow<List<Holiday>>
    suspend fun clearHolidays()
    suspend fun upsertHolidays(holidays: List<Holiday>)
    suspend fun upsertHoliday(holiday: Holiday)
}