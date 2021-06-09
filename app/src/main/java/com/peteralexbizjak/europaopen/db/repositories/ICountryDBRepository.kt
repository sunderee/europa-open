package com.peteralexbizjak.europaopen.db.repositories

import com.peteralexbizjak.europaopen.db.entities.CountryEntity

internal interface ICountryDBRepository {
    suspend fun storeCountries(vararg countries: CountryEntity): Boolean
    suspend fun retrieveCountries(): List<CountryEntity>
    suspend fun retrieveCountries(direction: String): List<CountryEntity>
    suspend fun isTableEmpty(): Boolean
    suspend fun clearTable()
}