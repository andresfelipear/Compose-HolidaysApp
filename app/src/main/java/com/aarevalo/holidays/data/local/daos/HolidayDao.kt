package com.aarevalo.holidays.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aarevalo.holidays.domain.entities.HolidayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HolidayDao {

    @Query("SELECT * FROM holidays")
    fun getHolidays(): Flow<List<HolidayEntity>>

    @Query("DELETE FROM holidays")
    suspend fun clearHolidays()

    @Upsert
    suspend fun upsertHolidays(holidays: List<HolidayEntity>)

    @Upsert
    suspend fun upsertHoliday(holiday: HolidayEntity)
}
