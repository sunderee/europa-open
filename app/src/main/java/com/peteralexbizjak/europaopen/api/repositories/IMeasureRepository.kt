package com.peteralexbizjak.europaopen.api.repositories

import com.peteralexbizjak.europaopen.api.models.measures.DomainDataModel
import com.peteralexbizjak.europaopen.api.models.measures.DomainModel
import com.peteralexbizjak.europaopen.api.models.measures.RuleModel

interface IMeasureRepository {
    suspend fun fetchDomains(): List<DomainModel>

    suspend fun fetchDomainIndicators(domainIDs: List<Int>): List<DomainDataModel>

    suspend fun fetchRules(countryCode: String, ruleCodes: List<Int>): List<RuleModel>
}