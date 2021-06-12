package com.peteralexbizjak.europaopen.api.repositories.implementations

import com.peteralexbizjak.europaopen.api.models.measures.DomainDataModel
import com.peteralexbizjak.europaopen.api.models.measures.DomainModel
import com.peteralexbizjak.europaopen.api.models.measures.RuleModel
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.services.MeasuresService
import com.peteralexbizjak.europaopen.utils.exceptions.ApiException

internal class MeasureRepository(
    private val service: MeasuresService
) : IMeasureRepository {
    override suspend fun fetchDomains(): List<DomainModel> = service.fetchDomains()

    @Throws(ApiException::class)
    override suspend fun fetchDomainIndicators(domainIDs: List<Int>): List<DomainDataModel> {
        if (domainIDs.isEmpty()) {
            throw ApiException("No domain IDs were provided")
        }
        return service.fetchDomainIndicators(domainIDs.joinToString(","))
    }

    @Throws(ApiException::class)
    override suspend fun fetchRules(countryCode: String, ruleCodes: List<Int>): List<RuleModel> {
        if (ruleCodes.isEmpty()) {
            throw ApiException("No domain IDs were provided")
        }
        return service.fetchDomainIndicatorRules(countryCode, ruleCodes.joinToString(","))
    }
}