package com.peteralexbizjak.europaopen.api.repositories.implementations

import com.peteralexbizjak.europaopen.api.models.CountryModel
import com.peteralexbizjak.europaopen.api.repositories.ICountryRepository
import com.peteralexbizjak.europaopen.api.services.CountryService
import com.peteralexbizjak.europaopen.utils.constants.Direction
import com.peteralexbizjak.europaopen.utils.exceptions.ApiException

internal class CountryRepository(
    private val service: CountryService
) : ICountryRepository {

    @Throws(ApiException::class)
    override suspend fun fetchCountries(direction: Direction): List<CountryModel> {
        val countries = if (direction == Direction.FROM) {
            service.countriesFrom()
        } else {
            service.countriesTo()
        }
        if (countries.isEmpty()) {
            throw ApiException("No countries fetched from the API")
        }
        return countries
    }
}