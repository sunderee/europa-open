package com.peteralexbizjak.europaopen.api.repositories

import com.peteralexbizjak.europaopen.api.models.RegionModel

internal interface IRegionRepository {
    suspend fun requestAllRegions(): List<RegionModel>

    suspend fun requestRegionsPerCountry(countryCode: String): List<RegionModel>
}