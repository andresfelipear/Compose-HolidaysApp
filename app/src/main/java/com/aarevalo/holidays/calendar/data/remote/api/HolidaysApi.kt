package com.aarevalo.holidays.calendar.data.remote.api

interface HolidaysApi {

    suspend fun fetchHolidaysPerCountry()
}