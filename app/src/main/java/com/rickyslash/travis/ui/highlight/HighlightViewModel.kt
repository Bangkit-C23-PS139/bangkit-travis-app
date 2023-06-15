package com.rickyslash.travis.ui.highlight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickyslash.travis.api.response.HighlightDataItem
import com.rickyslash.travis.data.TravelRepository
import com.rickyslash.travis.model.CurrentStateModel
import com.rickyslash.travis.model.CurrentStatePreferences

class HighlightViewModel(private val currentPreferences: CurrentStatePreferences, travelRepository: TravelRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    val highlight: LiveData<PagingData<HighlightDataItem>> =
        travelRepository.getHighlight(currentPreferences.getCurrentState().currentLocation!!).cachedIn(viewModelScope)

    fun getCurrentCity(): String? {
        return currentPreferences.getCurrentState().currentLocation
    }

}