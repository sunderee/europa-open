package com.peteralexbizjak.europaopen.ui.viewmodels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteralexbizjak.europaopen.api.models.region.RegionModel
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.IRegionRepository
import com.peteralexbizjak.europaopen.ui.viewmodels.models.GenericResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

internal class MeasuresViewModel(
    private val measureRepository: IMeasureRepository,
    private val regionRepository: IRegionRepository
) : ViewModel() {

    @Parcelize
    data class Rule(
        val title: String,
        val comment: String
    ) : Parcelable

    @Parcelize
    data class Indicator(
        val domainID: Int,
        val title: String,
        val comment: String? = null,
        val rules: List<Rule> = emptyList()
    ) : Parcelable

    @Parcelize
    data class Domain(
        val title: String,
        val indicators: List<Indicator>
    ) : Parcelable

    private val regionData by lazy { MutableLiveData<GenericResponse<List<RegionModel>>>() }
    fun observeRegionData(): LiveData<GenericResponse<List<RegionModel>>> = regionData

    private val domainData by lazy { MutableLiveData<GenericResponse<List<Domain>>>() }
    fun observeDomainData(): LiveData<GenericResponse<List<Domain>>> = domainData

    fun requestDomainData(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            domainData.postValue(GenericResponse.Loading)
            val domains = measureRepository.fetchDomains()
            val indicators = measureRepository
                .fetchDomainIndicators(domains.map { it.id })
                .flatMap { it.indicators }
                .map { indicator ->
                    val indicatorID = indicator.id
                    val rules = measureRepository.fetchRules(
                        countryCode,
                        listOf(indicatorID, *indicator.rules.toTypedArray())
                    ).flatMap { it.data }
                    Indicator(
                        rules
                            .find { it.id == indicatorID }
                            ?.domainID ?: -1,
                        indicator.name,
                        rules
                            .find { it.id == indicatorID }
                            ?.restrictions ?: "No data",
                        rules
                            .filter { indicator.rules.contains(it.id) }
                            .map { Rule(it.indicator, it.restrictions) }
                    )
                }
            val domainIndicatorRules = domains.map { domain ->
                Domain(
                    domain.name,
                    indicators.filter { it.domainID == domain.id }
                )
            }
            domainData.postValue(
                if (domainIndicatorRules.isNotEmpty()) GenericResponse.Success(domainIndicatorRules)
                else GenericResponse.Error("Could not pull domain data")
            )
        }
    }

    fun requestRegionsData(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            regionData.postValue(GenericResponse.Loading)
            val data = regionRepository.requestRegionsPerCountry(countryCode)
            regionData.postValue(
                if (data.isNotEmpty()) GenericResponse.Success(data)
                else GenericResponse.Error("No data available for $countryCode")
            )
        }
    }
}