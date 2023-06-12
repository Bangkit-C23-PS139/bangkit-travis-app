package com.rickyslash.travis.ui.main.pages.bonding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.dummy.DummyApiConfig
import com.rickyslash.travis.api.dummy.dummyresponse.DummyBondingItem
import com.rickyslash.travis.api.dummy.dummyresponse.DummyBondingResponse
import com.rickyslash.travis.api.entity.BondingData
import com.rickyslash.travis.api.response.BondingListDataItem
import com.rickyslash.travis.api.response.BondingListResponse
import com.rickyslash.travis.api.response.LinkToBondingResponse
import com.rickyslash.travis.helper.getFirstWord
import com.rickyslash.travis.model.UserModel
import com.rickyslash.travis.model.UserSharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BondingViewModel(private val userPreferences: UserSharedPreferences) : ViewModel() {

//    private val _listDummyBondingItem = MutableLiveData<List<DummyBondingItem>>()
//    val listDummyBondingItem: LiveData<List<DummyBondingItem>> = _listDummyBondingItem

    private val _listBondingData = MutableLiveData<List<BondingListDataItem>>()
    val listBondingData: LiveData<List<BondingListDataItem>> = _listBondingData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    private val _joinResponseMessage = MutableLiveData<String?>()
    val joinResponseMessage: LiveData<String?> = _joinResponseMessage

    fun getPreferences(): UserModel {
        return userPreferences.getUser()
    }

    fun getBondingList() {
        val preferences = getPreferences()
        _isLoading.value = true
        val client = ApiConfig.getApiService(preferences.accessToken, preferences.refreshToken).getBondingList()
        client.enqueue(object : Callback<BondingListResponse> {
            override fun onResponse(
                call: Call<BondingListResponse>,
                response: Response<BondingListResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = getFirstWord(responseBody.message) != "Successfully"
                        _listBondingData.value = responseBody.data
                        _responseMessage.value = responseBody.message
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<BondingListResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }

    fun joinBonding(bondingId: String) {
        val client = ApiConfig.getApiService().linkToBonding(bondingId)
        client.enqueue(object : Callback<LinkToBondingResponse> {
            override fun onResponse(
                call: Call<LinkToBondingResponse>,
                response: Response<LinkToBondingResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _joinResponseMessage.value = responseBody.message
                        _joinResponseMessage.value = null
                    }
                } else {
                    _joinResponseMessage.value = response.message()
                    _joinResponseMessage.value = null
                }
            }

            override fun onFailure(call: Call<LinkToBondingResponse>, t: Throwable) {
                _joinResponseMessage.value = t.message
                _joinResponseMessage.value = null
            }
        })
    }
    
}