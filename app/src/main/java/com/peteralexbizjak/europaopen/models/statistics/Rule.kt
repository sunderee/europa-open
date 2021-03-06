package com.peteralexbizjak.europaopen.models.statistics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rule(
    val id: Int,
    val title: String,
    val comment: String
) : Parcelable