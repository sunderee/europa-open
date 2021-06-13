package com.peteralexbizjak.europaopen.api.repositories

import com.peteralexbizjak.europaopen.api.models.region.TravelModel

interface ITravelRepository {
    suspend fun fetchTravelInfo(
        start: String,
        destination: String,
        transient: String? = null
    ): List<TravelModel>
}