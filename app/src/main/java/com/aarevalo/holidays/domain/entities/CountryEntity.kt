package com.aarevalo.holidays.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aarevalo.holidays.domain.model.Country

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val code: String
){
    fun toCountry(): Country {
        return Country(name = name, code = code)
    }

    companion object{
        fun fromCountry(country: Country): CountryEntity {
            return CountryEntity(name = country.name, code = country.code)
        }
    }
}
