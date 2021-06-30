package com.peteralexbizjak.europaopen.ui.travel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteralexbizjak.europaopen.api.models.travel.TravelModel
import com.peteralexbizjak.europaopen.api.repositories.ITravelRepository
import com.peteralexbizjak.europaopen.models.GenericResponse
import com.peteralexbizjak.europaopen.utils.exceptions.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class TravelViewModel(
    private val travelRepository: ITravelRepository
) : ViewModel() {
    private val travelLiveData by lazy { MutableLiveData<GenericResponse<List<TravelModel>>>() }
    val travelData: LiveData<GenericResponse<List<TravelModel>>> get() = travelLiveData

    fun requestTravelData(
        startingCountry: String,
        destinationCountry: String,
        transientCountry: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            travelLiveData.postValue(GenericResponse.Loading)
            try {
                val data = travelRepository.fetchTravelInfo(
                    startingCountry,
                    destinationCountry,
                    transientCountry
                )
                travelLiveData.postValue(
                    if (data.size >= 2 || data.size <= 3) GenericResponse.Success(data)
                    else GenericResponse.Error("Invalid data length")
                )
            } catch (e: ApiException) {
                travelLiveData.postValue(GenericResponse.Error(e.message ?: "Fatal error"))
            }
        }
    }
}