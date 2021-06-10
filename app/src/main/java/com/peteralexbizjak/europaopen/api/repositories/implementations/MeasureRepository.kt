package com.peteralexbizjak.europaopen.api.repositories.implementations

import com.peteralexbizjak.europaopen.api.models.DomainModel
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.services.MeasuresService

internal class MeasureRepository(
    private val service: MeasuresService
) : IMeasureRepository {
    override suspend fun fetchDomains(): List<DomainModel> = service.fetchDomains()
}