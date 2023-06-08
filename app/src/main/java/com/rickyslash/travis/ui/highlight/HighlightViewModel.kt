package com.rickyslash.travis.ui.highlight

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.response.DummyHighlightResponse
import com.rickyslash.travis.api.response.HighlightItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HighlightViewModel(): ViewModel() {

    private val _listHighlightItem = MutableLiveData<List<HighlightItem>>()
    val listHighlightItem: LiveData<List<HighlightItem>> = _listHighlightItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun getHighlights() {
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWJDTGNBTk9PMzE1WllEYWIiLCJpYXQiOjE2ODYxMTg0MzB9.zPUdYarP5faDNnSbooHwfXhKIfDlVhYYERf86KzDb2o"
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getHighlights()
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

    companion object {
        private val TAG = HighlightViewModel::class.java.simpleName
    }

}