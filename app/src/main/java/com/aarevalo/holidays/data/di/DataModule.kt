package com.aarevalo.holidays.data.di

import android.content.Context
import androidx.room.Room
import com.aarevalo.holidays.data.local.DefaultHolidayCache
import com.aarevalo.holidays.data.local.HolidaysDatabase
import com.aarevalo.holidays.data.local.RoomCountryLocalDataSource
import com.aarevalo.holidays.data.local.RoomHolidayLocalDataSource
import com.aarevalo.holidays.data.local.daos.CountryDao
import com.aarevalo.holidays.data.local.daos.HolidayDao
import com.aarevalo.holidays.data.remote.HolidaysApi
import com.aarevalo.holidays.data.remote.repository.DefaultRemoteRepository
import com.aarevalo.holidays.domain.CountryLocalDataSource
import com.aarevalo.holidays.domain.HolidayLocalDataSource
import com.aarevalo.holidays.domain.HolidayCache
import com.aarevalo.holidays.domain.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.boguszpawlowski.composecalendar.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
                if(BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            build()
        }
        return Retrofit.Builder()
            .baseUrl(HolidaysApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
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

    @Provides
    @Singleton
    fun provideHolidaysApi(retrofit: Retrofit): HolidaysApi {
        return retrofit.create(HolidaysApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(
        holidaysApi: HolidaysApi,
        @ApplicationContext context: Context
    ): RemoteRepository {
        return DefaultRemoteRepository(holidaysApi, context)
    }
}