package com.rickyslash.travis.ui.main.pages.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.dummy.DummyApiConfig
import com.rickyslash.travis.api.dummy.dummyresponse.DummyServiceResponse
import com.rickyslash.travis.api.dummy.dummyresponse.ServiceItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceViewModel : ViewModel() {

    private val _listServiceItem = MutableLiveData<List<ServiceItem>>()
    val listServiceItem: LiveData<List<ServiceItem>> = _listServiceItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun getServices() {
        _isLoading.value = true
        val client = DummyApiConfig.getApiService().getServices()
        client.enqueue(object : Callback<DummyServiceResponse> {
            override fun onResponse(
                call: Call<DummyServiceResponse>,
                response: Response<DummyServiceResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = responseBody.error
                        _responseMessage.value = responseBody.message
                        _listServiceItem.value = responseBody.listService
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<DummyServiceResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }
}