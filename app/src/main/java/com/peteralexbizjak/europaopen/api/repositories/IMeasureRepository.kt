package com.peteralexbizjak.europaopen.api.repositories

import com.peteralexbizjak.europaopen.api.models.DomainModel

internal interface IMeasureRepository {
    suspend fun fetchDomains(): List<DomainModel>
}