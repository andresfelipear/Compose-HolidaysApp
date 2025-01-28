package com.aarevalo.holidays.data.remote.response

import com.aarevalo.holidays.domain.model.Holiday
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class HolidaySchema(
    @SerializedName("date") val date: String,
    @SerializedName("localName")val localName: String,
    @SerializedName("name")val name: String,
    @SerializedName("countryCode")val countryCode: String,
    @SerializedName("fixed") val fixed: Boolean,
    @SerializedName("global")val global: Boolean,
    @SerializedName("counties")val counties: List<String>,
    @SerializedName("launchYear")val launchYear: Int,
    @SerializedName("types")val types: List<String>
){
    fun toHoliday(): Holiday {
        return Holiday(
            name = name,
            date = LocalDate.parse(date)
        )
    }

    companion object
    {
        fun fromHoliday(holiday: Holiday): HolidaySchema {
            return HolidaySchema(
                date = holiday.date.toString(),
                localName = "",
                name = holiday.name,
                countryCode = "",
                fixed = false,
                global = false,
                counties = emptyList(),
                launchYear = 0,
                types = emptyList()
            )
        }
    }
}
