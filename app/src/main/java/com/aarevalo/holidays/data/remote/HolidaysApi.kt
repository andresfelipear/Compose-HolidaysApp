package com.aarevalo.holidays.data.remote

import com.aarevalo.holidays.data.remote.response.HolidayResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidaysApi {

    @GET("/api/v3/PublicHolidays/{Year}/{CountryCode}")
    suspend fun fetchHolidaysPerCountryAndYear(@Path("Year") year: Int, @Path("CountryCode") countryCode: String): com.aarevalo.holidays.data.remote.response.HolidayResponse
}