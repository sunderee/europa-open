package com.peteralexbizjak.europaopen.ui.viewmodels.models.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rule(
    val title: String,
    val comment: String
) : Parcelable