package com.rickyslash.travis.ui.main.pages.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickyslash.travis.api.response.ServiceDataItem
import com.rickyslash.travis.data.TravelRepository
import com.rickyslash.travis.model.CurrentStatePreferences

class ServiceViewModel(private val currentPreferences: CurrentStatePreferences, travelRepository: TravelRepository): ViewModel() {

    val service: LiveData<PagingData<ServiceDataItem>> =
        travelRepository.getService(currentPreferences.getCurrentState().currentLocation!!).cachedIn(viewModelScope)

    fun getCurrentCity(): String? {
        return currentPreferences.getCurrentState().currentLocation
    }
}