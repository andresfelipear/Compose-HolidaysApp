package com.aarevalo.holidays.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HolidayResponse(
    val holidays: List<com.aarevalo.holidays.data.remote.response.HolidaySchema>
)
