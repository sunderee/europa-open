package com.peteralexbizjak.europaopen.viewmodels

import androidx.lifecycle.ViewModel
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.IRegionRepository

internal class MeasuresViewModel(
    private val measureRepository: IMeasureRepository,
    private val regionRepository: IRegionRepository
) : ViewModel() {}