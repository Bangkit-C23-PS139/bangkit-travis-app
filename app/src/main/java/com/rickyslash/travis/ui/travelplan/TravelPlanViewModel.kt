package com.rickyslash.travis.ui.travelplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.dummy.DummyApiConfig
import com.rickyslash.travis.api.dummy.dummyresponse.DummyTravelPlanResponse
import com.rickyslash.travis.api.dummy.dummyresponse.TravelPlanItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravelPlanViewModel: ViewModel() {

    private val _listTravelPlanItem = MutableLiveData<List<TravelPlanItem>>()
    val listTravelPlanItem: LiveData<List<TravelPlanItem>> = _listTravelPlanItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun getTravelPlan() {
        _isLoading.value = true
        val client = DummyApiConfig.getApiService().getTravelPlan()
        client.enqueue(object : Callback<DummyTravelPlanResponse> {
            override fun onResponse(
                call: Call<DummyTravelPlanResponse>,
                response: Response<DummyTravelPlanResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = responseBody.error
                        _responseMessage.value = responseBody.message
                        _listTravelPlanItem.value = responseBody.listTravelPlan
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<DummyTravelPlanResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }
}