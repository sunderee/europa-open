package com.peteralexbizjak.europaopen.db.daos

import androidx.room.*
import com.peteralexbizjak.europaopen.db.entities.DomainEntity
import com.peteralexbizjak.europaopen.db.entities.DomainWithIndicators
import com.peteralexbizjak.europaopen.db.entities.IndicatorEntity

@Dao
internal interface DomainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeDomain(domain: DomainEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeIndicators(indicators: List<IndicatorEntity>)

    @Transaction
    @Query("select * from domains")
    suspend fun retrieveAllDomains(): List<DomainWithIndicators>
}