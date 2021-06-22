package com.peteralexbizjak.europaopen.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.peteralexbizjak.europaopen.db.entities.DomainEntity
import com.peteralexbizjak.europaopen.db.entities.DomainWithIndicators
import com.peteralexbizjak.europaopen.db.entities.IndicatorEntity

@Dao
internal interface DomainDao {
    @Insert
    suspend fun storeDomain(domain: DomainEntity)

    @Insert
    suspend fun storeIndicators(indicators: List<IndicatorEntity>)

    @Transaction
    @Query("select * from domains")
    suspend fun retrieveAllDomains(): List<DomainWithIndicators>
}