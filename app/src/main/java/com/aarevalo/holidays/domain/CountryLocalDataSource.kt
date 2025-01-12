package com.aarevalo.holidays.domain

import com.aarevalo.holidays.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryLocalDataSource {
    val taskFlow: Flow<List<Country>>
    suspend fun clearCountries()
    suspend fun upsertCountries(countries: List<Country>)
    suspend fun upsertCountry(country: Country)
}