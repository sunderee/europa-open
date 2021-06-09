package com.peteralexbizjak.europaopen.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CountryModel(
    @SerialName("ISO3")
    val shortName: String,

    @SerialName("name")
    val longName: String
)