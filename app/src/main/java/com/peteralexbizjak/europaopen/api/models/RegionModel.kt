package com.peteralexbizjak.europaopen.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RegionModel(
    @SerialName("ISO3")
    val countryCode: String,

    @SerialName("name")
    val region: String,

    @SerialName("colour")
    val color: String
)