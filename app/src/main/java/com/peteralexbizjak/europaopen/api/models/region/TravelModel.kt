package com.peteralexbizjak.europaopen.api.models.region

import com.peteralexbizjak.europaopen.api.models.measures.IndicatorModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TravelModel(
    @SerialName("nutscode")
    val countryCode: String,

    @SerialName("indicators")
    val data: List<IndicatorModel>
)