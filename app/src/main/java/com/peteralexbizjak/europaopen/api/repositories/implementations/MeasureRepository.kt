package com.peteralexbizjak.europaopen.api.repositories.implementations

import com.peteralexbizjak.europaopen.api.models.DomainDataModel
import com.peteralexbizjak.europaopen.api.models.DomainModel
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.services.MeasuresService
import com.peteralexbizjak.europaopen.utils.exceptions.ApiException

internal class MeasureRepository(
    private val service: MeasuresService
) : IMeasureRepository {
    override suspend fun fetchDomains(): List<DomainModel> = service.fetchDomains()

    @Throws(ApiException::class)
    override suspend fun fetchDomainIndicators(vararg domainIDs: Int): List<DomainDataModel> {
        if (domainIDs.isEmpty()) {
            throw ApiException("No domain IDs were provided")
        }
        return service.fetchDomainIndicators(domainIDs.joinToString(","))
    }
}