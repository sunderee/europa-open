package com.peteralexbizjak.europaopen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteralexbizjak.europaopen.api.models.RegionModel
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.IRegionRepository
import com.peteralexbizjak.europaopen.viewmodels.models.GenericResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MeasuresViewModel(
    private val measureRepository: IMeasureRepository,
    private val regionRepository: IRegionRepository
) : ViewModel() {

    data class Rule(val title: String, val comment: String)
    data class Indicator(val title: String, val rules: List<Rule>)
    data class Domain(val title: String, val indicators: List<Indicator>)

    private val regionData by lazy { MutableLiveData<GenericResponse<List<RegionModel>>>() }
    fun observeRegionData(): LiveData<GenericResponse<List<RegionModel>>> = regionData

    private val domainData by lazy { MutableLiveData<GenericResponse<List<Domain>>>() }
    fun observeDomainData(): LiveData<GenericResponse<List<Domain>>> = domainData

    fun requestDomainData(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            domainData.postValue(GenericResponse.Loading)
            val domains = measureRepository.fetchDomains()
            val indicators = measureRepository.fetchDomainIndicators(domains.map { it.id })

            val allRules = measureRepository
                .fetchRules(
                    countryCode,
                    indicators.flatMap { it.indicators.flatMap { indicator -> indicator.rules } })
                .flatMap { it.data }

            val domainIndicatorRules = domains.map { model ->
                Domain(
                    model.name,
                    indicators.flatMap { domain ->
                        domain.indicators
                            .map { indicator ->
                                Indicator(
                                    indicator.name,
                                    indicator.rules.map { rule ->
                                        val tmpRule = allRules.first { it.id == rule }
                                        Rule(tmpRule.indicator, tmpRule.restrictions)
                                    })
                            }
                    })
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