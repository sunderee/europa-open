package com.peteralexbizjak.europaopen.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DomainIndicatorModel(
    @SerialName("indicator_id")
    val id: Int,

    @SerialName("indicator_name")
    val name: String,

    @SerialName("rules")
    val rules: List<Int>
)