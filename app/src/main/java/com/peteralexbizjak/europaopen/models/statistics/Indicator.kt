package com.peteralexbizjak.europaopen.models.statistics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Indicator(
    val domainID: Int,
    val title: String,
    val comment: String? = null,
    val rules: List<Rule> = emptyList()
) : Parcelable