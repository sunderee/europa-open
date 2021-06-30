package com.peteralexbizjak.europaopen.db.repositories

import com.peteralexbizjak.europaopen.db.entities.RegionEntity

internal interface IRegionDBRepository {
    suspend fun storeRegions(regions: List<RegionEntity>)
    suspend fun retrieveRegionsByCountryCode(countryCode: String): List<RegionEntity>
}