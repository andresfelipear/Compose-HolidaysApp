package com.aarevalo.holidays.domain.usecases

import com.aarevalo.holidays.domain.HolidayCache
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.Holiday
import com.aarevalo.holidays.domain.model.State
import com.aarevalo.holidays.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FetchHolidaysUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val holidaysCache: HolidayCache
) {
    private var holidays: List<Holiday> = emptyList()

    suspend operator fun invoke(year: Int, country: Country, state: State? = null): List<Holiday> {
        return withContext(Dispatchers.IO) {
            holidays = holidaysCache.getHolidays() ?: run {
                val currentYearHolidays = remoteRepository.fetchHolidaysFromNetwork(year, country, state)
                val previousYearHolidays = remoteRepository.fetchHolidaysFromNetwork(year - 1, country, state)
                val nextYearHolidays = remoteRepository.fetchHolidaysFromNetwork(year + 1, country, state)
                val allHolidays = currentYearHolidays + previousYearHolidays + nextYearHolidays
                holidaysCache.refreshHolidays(allHolidays)
                allHolidays
            }
            holidays
        }
    }
}