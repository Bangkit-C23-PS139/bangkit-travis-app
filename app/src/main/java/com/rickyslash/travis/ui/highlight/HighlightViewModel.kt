package com.rickyslash.travis.ui.highlight

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickyslash.travis.api.response.HighlightDataItem
import com.rickyslash.travis.data.TravelRepository
import com.rickyslash.travis.model.CurrentStatePreferences

class HighlightViewModel(private val currentPreferences: CurrentStatePreferences, travelRepository: TravelRepository): ViewModel() {

    val highlight: LiveData<PagingData<HighlightDataItem>> =
        travelRepository.getHighlight(currentPreferences.getCurrentState().currentLocation!!).cachedIn(viewModelScope)

    fun getCurrentCity(): String? {
        return currentPreferences.getCurrentState().currentLocation
    }

}