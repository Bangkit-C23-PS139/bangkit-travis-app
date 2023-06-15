package com.rickyslash.travis.ui.highlight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickyslash.travis.api.dummy.DummyApiConfig
import com.rickyslash.travis.api.dummy.dummyresponse.DummyHighlightResponse
import com.rickyslash.travis.api.dummy.dummyresponse.HighlightItem
import com.rickyslash.travis.api.response.HighlightDataItem
import com.rickyslash.travis.data.TravelRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HighlightViewModel(travelRepository: TravelRepository): ViewModel() {

    private val _listHighlightItem = MutableLiveData<List<HighlightItem>>()
    val listHighlightItem: LiveData<List<HighlightItem>> = _listHighlightItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    val highlight: LiveData<PagingData<HighlightDataItem>> =
        travelRepository.getHighlight().cachedIn(viewModelScope)

    fun getHighlights() {
        _isLoading.value = true
        val client = DummyApiConfig.getApiService().getHighlights()
        client.enqueue(object : Callback<DummyHighlightResponse> {
            override fun onResponse(
                call: Call<DummyHighlightResponse>,
                response: Response<DummyHighlightResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = responseBody.error
                        _responseMessage.value = responseBody.message
                        _listHighlightItem.value = responseBody.listHighlight
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<DummyHighlightResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }

        })
    }

}