package com.aarevalo.holidays.domain.usecases

import com.aarevalo.holidays.domain.CountryLocalDataSource
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchListOfCountriesUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val countryLocalDataSource: CountryLocalDataSource
){

    suspend operator fun invoke(): List<Country> {
        return withContext(Dispatchers.IO) {
            val countries = countryLocalDataSource.countries
            countries.ifEmpty {
                val fetchedCountries = remoteRepository.fetchCountriesFromNetwork()
                countryLocalDataSource.upsertCountries(fetchedCountries)
                fetchedCountries
            }
        }
    }
}