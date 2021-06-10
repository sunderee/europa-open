package com.peteralexbizjak.europaopen.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class IndicatorModel(
    @SerialName("indicator_id")
    val id: Int,

    @SerialName("indicator_name")
    val indicator: String,

    @SerialName("comment")
    val restrictions: String,

    @SerialName("last_update")
    val lastUpdate: String,

    @SerialName("rules")
    val additionalRules: List<Int>
)