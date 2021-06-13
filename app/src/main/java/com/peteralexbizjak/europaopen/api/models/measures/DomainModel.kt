package com.peteralexbizjak.europaopen.api.models.measures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DomainModel(
    @SerialName("domain_id")
    val id: Int,

    @SerialName("domain_name")
    val name: String
)