package com.aarevalo.holidays.data.remote.response

import com.google.gson.annotations.SerializedName

data class CountrySchema(
    @SerializedName("name") val name: String,
    @SerializedName("countryCode") val code: String,
)
