package com.aarevalo.holidays.di

import com.aarevalo.holidays.data.local.HolidaysCache
import com.aarevalo.holidays.data.remote.HolidaysApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.boguszpawlowski.composecalendar.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val baseUrl = "https://date.nager.at"

        val httpClient = OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
              if(BuildConfig.DEBUG) {
                  level = HttpLoggingInterceptor.Level.BODY
              }
            })
            build()
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHolidaysApi(retrofit: Retrofit): HolidaysApi {
        return retrofit.create(HolidaysApi::class.java)
    }

    @Provides
    @Singleton
    fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}