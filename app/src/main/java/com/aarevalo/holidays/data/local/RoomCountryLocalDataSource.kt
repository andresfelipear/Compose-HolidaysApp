package com.aarevalo.holidays.data.local

import com.aarevalo.holidays.data.local.daos.CountryDao
import com.aarevalo.holidays.domain.CountryLocalDataSource
import com.aarevalo.holidays.domain.entities.CountryEntity
import com.aarevalo.holidays.domain.model.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomCountryLocalDataSource @Inject constructor(
    private val countryDao: CountryDao,
    private val dispatcherIO: CoroutineDispatcher
): CountryLocalDataSource {
    override val taskFlow: Flow<List<Country>>
        get() = countryDao.getCountries().map {
            it.map { countryEntity ->
                countryEntity.toCountry()
            }
        }

    override suspend fun clearCountries() = withContext(dispatcherIO) {
        countryDao.clearCountries()
    }

    override suspend fun upsertCountries(countries: List<Country>) = withContext(dispatcherIO) {
        countryDao.upsertCountries(countries.map { CountryEntity.fromCountry(it) })
    }

    override suspend fun upsertCountry(country: Country) = withContext(dispatcherIO) {
        countryDao.upsertCountry(CountryEntity.fromCountry(country))
    }
}