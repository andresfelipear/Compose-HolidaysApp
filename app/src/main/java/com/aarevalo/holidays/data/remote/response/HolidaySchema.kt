package com.aarevalo.holidays.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HolidaySchema(
    @Json(name = "date") val date: String,
    @Json(name = "localName")val localName: String,
    @Json(name = "name")val name: String,
    @Json(name = "countryCode")val countryCode: String,
    @Json(name = "fixed") val fixed: Boolean,
    @Json(name = "global")val global: Boolean,
    @Json(name = "counties")val counties: List<String>,
    @Json(name = "launchYear")val launchYear: Int,
)
