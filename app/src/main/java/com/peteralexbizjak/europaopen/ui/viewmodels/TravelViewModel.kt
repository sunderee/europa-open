package com.peteralexbizjak.europaopen.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteralexbizjak.europaopen.api.models.travel.TravelModel
import com.peteralexbizjak.europaopen.api.repositories.ITravelRepository
import com.peteralexbizjak.europaopen.ui.viewmodels.models.GenericResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TravelViewModel(private val repository: ITravelRepository) : ViewModel() {
    private val travelInfo by lazy { MutableLiveData<GenericResponse<List<TravelModel>>>() }
    fun observeTravelInfo(): LiveData<GenericResponse<List<TravelModel>>> = travelInfo

    fun requestTravelInfo(destination: String) {
        viewModelScope.launch(Dispatchers.IO) {
            travelInfo.postValue(GenericResponse.Loading)
            val destinations = destination.split("/")
            if (destinations.isEmpty() || destinations.size == 1) {
                travelInfo.postValue(GenericResponse.Error("No destinations, or not enough destinations provided"))
            } else {
                val travelInfoResponse = repository.fetchTravelInfo(
                    destinations[0],
                    destinations[1],
                    if (destinations.size == 3) destinations[2] else null
                )
                if (travelInfoResponse.isNotEmpty()) {
                    travelInfo.postValue(GenericResponse.Success(travelInfoResponse))
                } else {
                    travelInfo.postValue(GenericResponse.Error("Error retrieving data from the API"))
                }
            }
        }
    }
}