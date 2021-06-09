package com.peteralexbizjak.europaopen.api.services

import com.peteralexbizjak.europaopen.api.models.CountryModel
import retrofit2.http.GET

internal interface CountryService {
    @GET("api/covid/v1/eutcdata/countries/en/from")
    suspend fun countriesFrom(): List<CountryModel>

    @GET("api/covid/v1/eutcdata/countries/en/to")
    suspend fun countriesTo(): List<CountryModel>
}