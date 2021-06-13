package com.peteralexbizjak.europaopen.api.models.measures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DomainDataModel(
    @SerialName("domain_id")
    val id: Int,

    @SerialName("indicators")
    val indicators: List<DomainIndicatorModel>
)