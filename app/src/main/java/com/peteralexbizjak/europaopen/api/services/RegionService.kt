package com.peteralexbizjak.europaopen.api.services

import com.peteralexbizjak.europaopen.api.models.region.RegionModel
import retrofit2.http.GET

internal interface RegionService {
    @GET("api/covid/v1/eutcdata/regions/en")
    suspend fun requestRegionData(): List<RegionModel>
}