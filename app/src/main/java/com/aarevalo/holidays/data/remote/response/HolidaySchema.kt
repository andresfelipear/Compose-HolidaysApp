package com.aarevalo.holidays.data.remote.response

import com.google.gson.annotations.SerializedName

data class HolidaySchema(
    @SerializedName("date") val date: String,
    @SerializedName("localName")val localName: String,
    @SerializedName("name")val name: String,
    @SerializedName("countryCode")val countryCode: String,
    @SerializedName("fixed") val fixed: Boolean,
    @SerializedName("global")val global: Boolean,
    @SerializedName("counties")val counties: List<String>,
    @SerializedName("launchYear")val launchYear: Int,
)
