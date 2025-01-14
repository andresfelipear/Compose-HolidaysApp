package com.aarevalo.holidays.domain.usecases

import com.aarevalo.holidays.data.remote.HolidaysApi
import com.aarevalo.holidays.domain.CountryLocalDataSource
import com.aarevalo.holidays.domain.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchListOfCountriesUseCase @Inject constructor(
    private val holidaysApi: HolidaysApi,
    private val countryLocalDataSource: CountryLocalDataSource
){

    suspend fun fetchListOfCountries(): List<Country> {
        return withContext(Dispatchers.IO) {
            val countries = countryLocalDataSource.countries
            countries.ifEmpty {
                val fetchedCountries = fetchCountriesFromNetwork()
                countryLocalDataSource.upsertCountries(fetchedCountries)
                fetchedCountries
            }
        }
    }

    private suspend fun fetchCountriesFromNetwork(): List<Country> {
        return holidaysApi.fetchListOfCountries().map {
            Country(name = it.name, code = it.code)
        }
    }
}