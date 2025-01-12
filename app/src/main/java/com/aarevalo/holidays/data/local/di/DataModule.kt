package com.aarevalo.holidays.data.local.di

import android.content.Context
import androidx.room.Room
import com.aarevalo.holidays.data.local.DefaultHolidayCache
import com.aarevalo.holidays.data.local.HolidaysDatabase
import com.aarevalo.holidays.data.local.RoomCountryLocalDataSource
import com.aarevalo.holidays.data.local.RoomHolidayLocalDataSource
import com.aarevalo.holidays.data.local.daos.CountryDao
import com.aarevalo.holidays.data.local.daos.HolidayDao
import com.aarevalo.holidays.domain.CountryLocalDataSource
import com.aarevalo.holidays.domain.HolidayLocalDataSource
import com.aarevalo.holidays.domain.HolidayCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): HolidaysDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HolidaysDatabase::class.java,
            "holidays.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHolidaysDao(database: HolidaysDatabase) = database.holidaysDao

    @Provides
    @Singleton
    fun provideCountriesDao(database: HolidaysDatabase) = database.countriesDao

    @Provides
    @Singleton
    fun providesHolidayLocalDataSource(
        holidaysDao: HolidayDao,
        dispatcherIO: CoroutineDispatcher
    ): HolidayLocalDataSource = RoomHolidayLocalDataSource(holidaysDao, dispatcherIO)

    @Provides
    @Singleton
    fun providesCountryLocalDataSource(
        countriesDao: CountryDao,
        dispatcherIO: CoroutineDispatcher
    ): CountryLocalDataSource = RoomCountryLocalDataSource(countriesDao, dispatcherIO)

    @Provides
    @Singleton
    fun provideHolidayCache(): HolidayCache = DefaultHolidayCache()
}