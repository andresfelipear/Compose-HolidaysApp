package com.aarevalo.holidays.data.remote

import com.aarevalo.holidays.data.remote.response.CountrySchema
import com.aarevalo.holidays.data.remote.response.HolidaySchema
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidaysApi {

    @GET("/api/v3/PublicHolidays/{Year}/{CountryCode}")
    suspend fun fetchHolidaysPerCountryAndYear(@Path("Year") year: Int, @Path("CountryCode") countryCode: String): List<HolidaySchema>

    @GET("/api/v3/AvailableCountries")
    suspend fun fetchListOfCountries() : List<CountrySchema>

    companion object {
        const val BASE_URL = "https://date.nager.at"
    }
}