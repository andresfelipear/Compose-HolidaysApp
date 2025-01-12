package com.aarevalo.holidays.domain

import com.aarevalo.holidays.domain.model.Country

interface CountryLocalDataSource {
    val countries: List<Country>
    suspend fun clearCountries()
    suspend fun upsertCountries(countries: List<Country>)
    suspend fun upsertCountry(country: Country)
}