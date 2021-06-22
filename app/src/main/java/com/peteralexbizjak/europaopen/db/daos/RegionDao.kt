package com.peteralexbizjak.europaopen.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.peteralexbizjak.europaopen.db.entities.RegionEntity

@Dao
internal interface RegionDao {
    @Insert
    suspend fun storeRegionData(regions: List<RegionEntity>)

    @Query("select * from regions where country = :countryCode")
    suspend fun retrieveAllRegionData(countryCode: String): List<RegionEntity>
}