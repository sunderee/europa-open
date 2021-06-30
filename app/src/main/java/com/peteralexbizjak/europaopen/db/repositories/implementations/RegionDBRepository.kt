package com.peteralexbizjak.europaopen.db.repositories.implementations

import com.peteralexbizjak.europaopen.db.daos.RegionDao
import com.peteralexbizjak.europaopen.db.entities.RegionEntity
import com.peteralexbizjak.europaopen.db.repositories.IRegionDBRepository
import com.peteralexbizjak.europaopen.utils.exceptions.DatabaseException

internal class RegionDBRepository(private val regionDao: RegionDao) : IRegionDBRepository {
    override suspend fun storeRegions(regions: List<RegionEntity>) {
        if (regions.isEmpty()) {
            throw DatabaseException("You haven't provided any region entities...")
        }
        regionDao.storeRegionData(regions)
    }

    override suspend fun retrieveRegionsByCountryCode(countryCode: String): List<RegionEntity> =
        regionDao.retrieveAllRegionData(countryCode)
}