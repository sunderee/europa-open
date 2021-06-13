package com.peteralexbizjak.europaopen.api.models.measures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class IndicatorModel(
    @SerialName("domain_id")
    val domainID: Int,

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
) : Parcelable