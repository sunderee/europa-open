package com.peteralexbizjak.europaopen.api.repositories.implementations

import com.peteralexbizjak.europaopen.api.models.travel.TravelModel
import com.peteralexbizjak.europaopen.api.repositories.ITravelRepository
import com.peteralexbizjak.europaopen.api.services.TravelService
import com.peteralexbizjak.europaopen.utils.exceptions.ApiException

class TravelRepository(private val service: TravelService) : ITravelRepository {
    @Throws(ApiException::class)
    override suspend fun fetchTravelInfo(
        start: String,
        destination: String,
        transient: String?
    ): List<TravelModel> {
        var constructPath = "$start/$destination"
        if (transient != null) {
            constructPath = "$constructPath/$transient"
        }

        val travelInfo = service.fetchTravelInfo(constructPath).toMutableList()
        if (travelInfo.isNotEmpty()) {
            if (travelInfo.size == 3) {
                val tmpTransit = travelInfo[1]
                travelInfo[1] = travelInfo[2]
                travelInfo[2] = tmpTransit
            }
            return travelInfo
        }
        throw ApiException("No travel info returned, what did you do?")
    }
}