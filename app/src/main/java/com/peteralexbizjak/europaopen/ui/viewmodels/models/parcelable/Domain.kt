package com.peteralexbizjak.europaopen.ui.viewmodels.models.parcelable

import android.os.Parcelable
import com.peteralexbizjak.europaopen.ui.viewmodels.models.parcelable.Indicator
import kotlinx.parcelize.Parcelize

@Parcelize
data class Domain(
    val title: String,
    val indicators: List<Indicator>
) : Parcelable