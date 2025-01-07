package com.aarevalo.holidays.domain.usecases

import com.aarevalo.holidays.data.remote.HolidaysApi
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.domain.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject


@Suppress("SENSELESS_COMPARISON")
class FetchHolidaysUseCase @Inject constructor(
    private val holidaysApi: HolidaysApi
){
    private var holidays :List<Holiday> = emptyList()

    private var lastNetworkRequestNano = 0L

    suspend fun fetchHolidays(
        year: Int,
        country: Country,
        state: State? = null
    ): List<Holiday>{
        return withContext(Dispatchers.IO){
            if(hasEnoughTimePassed()) {
                lastNetworkRequestNano = System.nanoTime()
                holidays = holidaysApi.fetchHolidaysPerCountryAndYear(year, country.code).filter { holiday ->
                    (holiday.counties == null) || holiday.counties.contains("${country.code}-${state?.code}")
                }.map { holidaySchema ->
                    Holiday(
                        name = holidaySchema.name,
                        date = LocalDate.parse(holidaySchema.date)
                    )
                }
                holidays
            } else {
                holidays
            }
        }
    }

    private fun hasEnoughTimePassed(): Boolean {
        return System.nanoTime() - lastNetworkRequestNano > THROTTLE_TIME_MILLIS * 1_000_000
    }

    companion object{
        const val THROTTLE_TIME_MILLIS = 5000L
    }
}