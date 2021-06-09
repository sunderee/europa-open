package com.peteralexbizjak.europaopen.api.repositories

import com.peteralexbizjak.europaopen.api.models.CountryModel
import com.peteralexbizjak.europaopen.utils.constants.Direction

internal interface ICountryRepository {
    suspend fun fetchCountries(direction: Direction): List<CountryModel>
}