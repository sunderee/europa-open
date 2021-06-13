package com.peteralexbizjak.europaopen.ui.viewmodels.models.parcelable

import android.os.Parcelable
import com.peteralexbizjak.europaopen.ui.viewmodels.models.parcelable.Rule
import kotlinx.parcelize.Parcelize

@Parcelize
data class Indicator(
    val domainID: Int,
    val title: String,
    val comment: String? = null,
    val rules: List<Rule> = emptyList()
) : Parcelable