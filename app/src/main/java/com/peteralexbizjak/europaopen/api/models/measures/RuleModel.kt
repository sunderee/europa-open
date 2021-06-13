package com.peteralexbizjak.europaopen.api.models.measures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RuleModel(
    @SerialName("nutscode")
    val countryCode: String,

    @SerialName("indicators")
    val data: List<IndicatorModel>
)