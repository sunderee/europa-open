package com.peteralexbizjak.europaopen.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RuleModel(
    @SerialName("nutscode")
    val countryCode: String,

    @SerialName("indicators")
    val data: List<IndicatorModel>
)