package com.aarevalo.holidays.data.remote.response

import com.aarevalo.holidays.domain.model.Country
import com.google.gson.annotations.SerializedName

data class CountrySchema(
    @SerializedName("name") val name: String,
    @SerializedName("countryCode") val code: String,
) {
    fun toCountry(): Country {
        return Country(name = name, code = code)
    }

    companion object {
        fun fromCountry(country: Country): CountrySchema {
            return CountrySchema(name = country.name, code = country.code)
        }
    }
}
