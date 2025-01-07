package com.aarevalo.holidays.domain.usecases

import com.aarevalo.holidays.data.local.HolidaysCache
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
    private val holidaysApi: HolidaysApi,
    private val holidaysCache: HolidaysCache
) {
    private var holidays: List<Holiday> = emptyList()


    suspend fun fetchHolidays(year: Int, country: Country, state: State? = null): List<Holiday> {
        return withContext(Dispatchers.IO) {
            holidays = holidaysCache.getHolidays() ?: fetchHolidaysFromNetwork(year, country, state)
            holidaysCache.refreshHolidays(holidays)
            holidays
        }
    }

    private suspend fun fetchHolidaysFromNetwork(
        year: Int,
        country: Country,
        state: State? = null
    ): List<Holiday> {
        return holidaysApi.fetchHolidaysPerCountryAndYear(year, country.code)
            .filter { holiday ->
                (holiday.counties == null) || holiday.counties.contains("${country.code}-${state?.code}")
            }
            .map { holidaySchema ->
                Holiday(name = holidaySchema.name, date = LocalDate.parse(holidaySchema.date))
            }
    }
}