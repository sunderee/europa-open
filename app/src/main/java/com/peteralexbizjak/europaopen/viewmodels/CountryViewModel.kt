package com.peteralexbizjak.europaopen.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteralexbizjak.europaopen.api.models.CountryModel
import com.peteralexbizjak.europaopen.api.repositories.ICountryRepository
import com.peteralexbizjak.europaopen.db.entities.CountryEntity
import com.peteralexbizjak.europaopen.db.repositories.ICountryDBRepository
import com.peteralexbizjak.europaopen.utils.constants.Direction
import com.peteralexbizjak.europaopen.viewmodels.models.GenericResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class CountryViewModel(
    private val apiRepository: ICountryRepository,
    private val databaseRepository: ICountryDBRepository
) : ViewModel() {
    private val countriesLiveData by lazy { MutableLiveData<GenericResponse<List<CountryModel>>>() }
    fun observeCountries(): LiveData<GenericResponse<List<CountryModel>>> = countriesLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val countriesTemporary by lazy { mutableListOf<CountryModel>() }
            countriesLiveData.postValue(GenericResponse.Loading)
            try {
                val isEmpty = databaseRepository.isTableEmpty()
                if (isEmpty) {
                    val from = apiRepository.fetchCountries(Direction.FROM).toMutableList()
                    val to = apiRepository.fetchCountries(Direction.TO).toMutableList()

                    val both = from.intersect(to)
                    both.forEach { bothModel ->
                        from.removeIf { it.shortName == bothModel.shortName }
                        to.removeIf { it.shortName == bothModel.shortName }
                    }

                    databaseRepository.storeCountries(
                        *from.map {
                            CountryEntity(
                                countryShortName = it.shortName,
                                countryLongName = it.longName,
                                appearsInDirection = "from"
                            )
                        }.toTypedArray(),
                        *to.map {
                            CountryEntity(
                                countryShortName = it.shortName,
                                countryLongName = it.longName,
                                appearsInDirection = "to"
                            )
                        }.toTypedArray(),
                        *both.map {
                            CountryEntity(
                                countryShortName = it.shortName,
                                countryLongName = it.longName,
                                appearsInDirection = "both"
                            )
                        }.toTypedArray()
                    )
                    countriesTemporary.apply {
                        addAll(from)
                        addAll(to)
                        addAll(both)
                    }
                } else {
                    countriesTemporary.apply {
                        addAll(databaseRepository
                            .retrieveCountries("from")
                            .map {
                                CountryModel(
                                    it.countryShortName,
                                    it.countryLongName,
                                    direction = it.appearsInDirection
                                )
                            })
                        addAll(databaseRepository
                            .retrieveCountries("to")
                            .map {
                                CountryModel(
                                    it.countryShortName,
                                    it.countryLongName,
                                    direction = it.appearsInDirection
                                )
                            })
                        addAll(databaseRepository
                            .retrieveCountries("both")
                            .map {
                                CountryModel(
                                    it.countryShortName,
                                    it.countryLongName,
                                    direction = it.appearsInDirection
                                )
                            })
                    }
                }
                countriesLiveData.postValue(
                    GenericResponse.Success(countriesTemporary
                        .toSet()
                        .sortedBy { it.longName })
                )
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Fatal app error"
                Log.e(CountryViewModel::class.simpleName, e.toString())
                countriesLiveData.postValue(GenericResponse.Error(errorMessage))
            }
        }
    }
}