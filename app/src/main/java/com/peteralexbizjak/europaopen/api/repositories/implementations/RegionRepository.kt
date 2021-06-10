package com.peteralexbizjak.europaopen.api.repositories.implementations

import com.peteralexbizjak.europaopen.api.models.RegionModel
import com.peteralexbizjak.europaopen.api.repositories.IRegionRepository
import com.peteralexbizjak.europaopen.api.services.RegionService

internal class RegionRepository(
    private val service: RegionService
) : IRegionRepository {
    override suspend fun requestRegionsPerCountry(countryCode: String): List<RegionModel> = service
        .requestRegionData()
        .filter { it.countryCode == countryCode }
}