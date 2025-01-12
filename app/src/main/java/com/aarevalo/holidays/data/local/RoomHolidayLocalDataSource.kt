package com.aarevalo.holidays.data.local

import com.aarevalo.holidays.data.local.daos.HolidayDao
import com.aarevalo.holidays.domain.HolidayLocalDataSource
import com.aarevalo.holidays.domain.entities.HolidayEntity
import com.aarevalo.holidays.domain.model.Holiday
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomHolidayLocalDataSource @Inject constructor(
    private val holidayDao: HolidayDao,
    private val dispatcherIO: CoroutineDispatcher
): HolidayLocalDataSource{

    override val taskFlow: Flow<List<Holiday>>
        get() = holidayDao.getHolidays().map {
            it.map { holidayEntity ->
                holidayEntity.toHoliday()
            }
        }

    override suspend fun clearHolidays() = withContext(dispatcherIO) {
        holidayDao.clearHolidays()
    }

    override suspend fun upsertHolidays(holidays: List<Holiday>) = withContext(dispatcherIO) {
        holidayDao.upsertHolidays(holidays.map { HolidayEntity.fromHoliday(it) })
    }

    override suspend fun upsertHoliday(holiday: Holiday) = withContext(dispatcherIO) {
        holidayDao.upsertHoliday(HolidayEntity.fromHoliday(holiday))
    }

}