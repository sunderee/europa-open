package com.peteralexbizjak.europaopen.api.repositories

import com.peteralexbizjak.europaopen.api.models.DomainDataModel
import com.peteralexbizjak.europaopen.api.models.DomainModel
import com.peteralexbizjak.europaopen.api.models.RuleModel

internal interface IMeasureRepository {
    suspend fun fetchDomains(): List<DomainModel>

    suspend fun fetchDomainIndicators(domainIDs: List<Int>): List<DomainDataModel>

    suspend fun fetchRules(countryCode: String, ruleCodes: List<Int>): List<RuleModel>
}