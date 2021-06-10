package com.peteralexbizjak.europaopen.api.services

import com.peteralexbizjak.europaopen.api.models.DomainDataModel
import com.peteralexbizjak.europaopen.api.models.DomainModel
import com.peteralexbizjak.europaopen.api.models.RuleModel
import retrofit2.http.GET
import retrofit2.http.Path

internal interface MeasuresService {
    @GET("api/covid/v1/eutcdata/domains/en")
    suspend fun fetchDomains(): List<DomainModel>

    @GET("api/covid/v1/eutcdata/indicators/en/{indicators}")
    suspend fun fetchDomainIndicators(
        @Path(
            value = "indicators",
            encoded = true
        ) indicators: String
    ): List<DomainDataModel>

    @GET("api/covid/v1/eutcdata/data/en/{code}/{rules}")
    suspend fun fetchDomainIndicatorRules(
        @Path(
            value = "code",
            encoded = true
        )
        countryCode: String,

        @Path(
            value = "rules",
            encoded = true
        )
        rules: String
    ): List<RuleModel>
}