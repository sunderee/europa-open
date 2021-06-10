package com.peteralexbizjak.europaopen.api.services

import com.peteralexbizjak.europaopen.api.models.DomainModel
import retrofit2.http.GET

internal interface MeasuresService {
    @GET("api/covid/v1/eutcdata/domains/en")
    suspend fun fetchDomains(): List<DomainModel>
}