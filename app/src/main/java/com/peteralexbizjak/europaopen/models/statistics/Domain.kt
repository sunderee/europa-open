package com.peteralexbizjak.europaopen.models.statistics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Domain(
    val id: Int,
    val title: String,
    val indicators: List<Indicator>
) : Parcelable