package com.peteralexbizjak.europaopen.db.repositories.implementations

import com.peteralexbizjak.europaopen.db.daos.DomainDao
import com.peteralexbizjak.europaopen.db.entities.DomainEntity
import com.peteralexbizjak.europaopen.db.entities.DomainWithIndicators
import com.peteralexbizjak.europaopen.db.entities.IndicatorEntity
import com.peteralexbizjak.europaopen.db.repositories.IDomainDBRepository

internal class DomainDBRepository(private val domainDao: DomainDao) : IDomainDBRepository {
    override suspend fun storeDomainAndIndicators(
        domain: DomainEntity,
        indicators: List<IndicatorEntity>
    ) {
        domainDao.apply {
            storeDomain(domain)
            storeIndicators(indicators)
        }
    }

    override suspend fun retrieveAllDomains(): List<DomainWithIndicators> =
        domainDao.retrieveAllDomains()
}