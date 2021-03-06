package com.peteralexbizjak.europaopen.api.models.measures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DomainIndicatorModel(
    @SerialName("indicator_id")
    val id: Int,

    @SerialName("indicator_name")
    val name: String,

    @SerialName("rules")
    val rules: List<Int>
)