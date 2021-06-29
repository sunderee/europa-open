package com.peteralexbizjak.europaopen.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteralexbizjak.europaopen.api.models.region.RegionModel
import com.peteralexbizjak.europaopen.api.repositories.IMeasureRepository
import com.peteralexbizjak.europaopen.api.repositories.IRegionRepository
import com.peteralexbizjak.europaopen.db.entities.DomainEntity
import com.peteralexbizjak.europaopen.db.entities.IndicatorEntity
import com.peteralexbizjak.europaopen.db.entities.RegionEntity
import com.peteralexbizjak.europaopen.db.repositories.IDomainDBRepository
import com.peteralexbizjak.europaopen.db.repositories.IRegionDBRepository
import com.peteralexbizjak.europaopen.models.GenericResponse
import com.peteralexbizjak.europaopen.models.statistics.Domain
import com.peteralexbizjak.europaopen.models.statistics.Indicator
import com.peteralexbizjak.europaopen.models.statistics.Rule
import com.peteralexbizjak.europaopen.utils.exceptions.DatabaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class StatisticsViewModel(
    private val measureRepository: IMeasureRepository,
    private val regionRepository: IRegionRepository,
    private val regionDBRepository: IRegionDBRepository,
    private val domainDBRepository: IDomainDBRepository
) : ViewModel() {
    private val domainLiveData by lazy { MutableLiveData<GenericResponse<List<Domain>>>() }
    val domainData: LiveData<GenericResponse<List<Domain>>> get() = domainLiveData

    private val regionLiveData by lazy { MutableLiveData<GenericResponse<List<RegionModel>>>() }
    val regionData get(): LiveData<GenericResponse<List<RegionModel>>> = regionLiveData

    fun requestDomainData(countryCode: String, persistLocally: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            domainLiveData.postValue(GenericResponse.Loading)
            val domains = measureRepository.fetchDomains()
            val indicators = measureRepository
                .fetchDomainIndicators(domains.map { it.id })
                .flatMap { it.indicators }
                .map { indicator ->
                    val indicatorID = indicator.id
                    val rules = measureRepository
                        .fetchRules(
                            countryCode,
                            listOf(indicatorID, *indicator.rules.toTypedArray())
                        )
                        .flatMap { it.data }
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
                            .map { Rule(it.id, it.indicator, it.restrictions) }
                    )
                }
            val domainIndicatorRules = domains.map { domain ->
                Domain(
                    domain.id,
                    domain.name,
                    indicators.filter { it.domainID == domain.id }
                )
            }
            if (persistLocally) {
                domainIndicatorRules.forEach { domain ->
                    domainDBRepository.storeDomainAndIndicators(
                        DomainEntity(
                            domainID = domain.id,
                            domainName = domain.title
                        ),
                        domain.indicators.map { indicator ->
                            IndicatorEntity(
                                belongingDomainID = indicator.domainID,
                                indicatorName = indicator.title,
                                rules = indicator.rules.joinToString(",") { it.id.toString() }
                            )
                        }
                    )
                }
            }
            domainLiveData.postValue(
                if (domainIndicatorRules.isNotEmpty()) GenericResponse.Success(domainIndicatorRules)
                else GenericResponse.Error("Could not pull domain data")
            )
        }
    }

    fun requestRegionsData(countryCode: String, persistLocally: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            regionLiveData.postValue(GenericResponse.Loading)
            val data = regionRepository.requestRegionsPerCountry(countryCode)
            try {
                if (persistLocally) {
                    regionDBRepository.storeRegions(data.map {
                        RegionEntity(
                            countryCode = it.countryCode,
                            regionName = it.region,
                            regionColor = it.color
                        )
                    })
                }
                regionLiveData.postValue(
                    if (data.isNotEmpty()) GenericResponse.Success(data)
                    else GenericResponse.Error("No data available for $countryCode")
                )
            } catch (e: DatabaseException) {
                regionLiveData.postValue(GenericResponse.Error(e.message ?: "Unknown errors"))
            }
        }
    }
}