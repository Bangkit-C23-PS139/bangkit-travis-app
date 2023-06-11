package com.rickyslash.travis.ui.main.pages.bonding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.response.BondingItem
import com.rickyslash.travis.api.response.DummyBondingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BondingViewModel : ViewModel() {

    private val _listBondingItem = MutableLiveData<List<BondingItem>>()
    val listBondingItem: LiveData<List<BondingItem>> = _listBondingItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun getBondingList() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getBondingList()
        client.enqueue(object : Callback<DummyBondingResponse> {
            override fun onResponse(
                call: Call<DummyBondingResponse>,
                response: Response<DummyBondingResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = responseBody.error
                        _responseMessage.value = responseBody.message
                        _listBondingItem.value = responseBody.listBonding
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<DummyBondingResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }
    
}