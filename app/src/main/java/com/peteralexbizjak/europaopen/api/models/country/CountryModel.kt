package com.peteralexbizjak.europaopen.api.models.country

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
internal data class CountryModel(
    @SerialName("ISO3")
    val shortName: String,

    @SerialName("name")
    val longName: String,

    @Transient
    val direction: String = "both",
)