package com.peteralexbizjak.europaopen.api.services

import com.peteralexbizjak.europaopen.api.models.travel.TravelModel
import retrofit2.http.GET
import retrofit2.http.Path

interface TravelService {
    @GET("api/covid/v1/eutcdata/fromto/en/{destinations}")
    suspend fun fetchTravelInfo(
        @Path(
            value = "destinations",
            encoded = true
        ) destination: String
    ): List<TravelModel>
}