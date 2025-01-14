package com.aarevalo.holidays.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aarevalo.holidays.domain.entities.CountryEntity

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    fun getCountries(): List<CountryEntity>

    @Query("DELETE FROM countries")
    suspend fun clearCountries()

    @Upsert
    suspend fun upsertCountries(countries: List<CountryEntity>)

    @Upsert
    suspend fun upsertCountry(country: CountryEntity)

    @Query("SELECT * FROM countries WHERE code = :code")
    suspend fun getCountryByCode(code: String): CountryEntity?
}
