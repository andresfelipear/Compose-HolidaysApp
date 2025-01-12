package com.aarevalo.holidays.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aarevalo.holidays.data.local.daos.CountryDao
import com.aarevalo.holidays.data.local.daos.HolidayDao
import com.aarevalo.holidays.domain.entities.CountryEntity
import com.aarevalo.holidays.domain.entities.HolidayEntity

@Database(
    entities = [
        CountryEntity::class,
        HolidayEntity::class
    ],
    version = 1
)
abstract class HolidaysDatabase: RoomDatabase() {
    abstract val holidaysDao: HolidayDao
    abstract val countriesDao: CountryDao
}