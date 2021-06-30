package com.peteralexbizjak.europaopen.db.repositories

import com.peteralexbizjak.europaopen.db.entities.DomainEntity
import com.peteralexbizjak.europaopen.db.entities.DomainWithIndicators
import com.peteralexbizjak.europaopen.db.entities.IndicatorEntity

internal interface IDomainDBRepository {
    suspend fun storeDomainAndIndicators(domain: DomainEntity, indicators: List<IndicatorEntity>)
    suspend fun retrieveAllDomains(): List<DomainWithIndicators>
}