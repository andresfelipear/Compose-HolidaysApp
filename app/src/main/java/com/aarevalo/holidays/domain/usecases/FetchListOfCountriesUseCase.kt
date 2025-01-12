package com.aarevalo.holidays.domain.usecases

import com.aarevalo.holidays.data.remote.HolidaysApi
import com.aarevalo.holidays.domain.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchListOfCountriesUseCase @Inject constructor(
    private val holidaysApi: HolidaysApi
){
    suspend fun fetchListOfCountries() : List<Country> {
        return withContext(Dispatchers.IO) {
            holidaysApi.fetchListOfCountries().map {
                Country(name = it.name, code = it.code)
            }
        }
    }

}