package com.peteralexbizjak.europaopen.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DomainDataModel(
    @SerialName("domain_id")
    val id: Int,

    @SerialName("indicators")
    val indicators: List<DomainIndicatorModel>
)