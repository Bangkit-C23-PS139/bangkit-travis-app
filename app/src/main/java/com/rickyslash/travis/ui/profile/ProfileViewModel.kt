package com.rickyslash.travis.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.entity.BondingData
import com.rickyslash.travis.api.response.SelfDataResponse
import com.rickyslash.travis.model.CurrentStateModel
import com.rickyslash.travis.model.CurrentStatePreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val currentPreferences: CurrentStatePreferences): ViewModel() {

    private val _listSelfUserBondingItem = MutableLiveData<List<BondingData>>()
    val listSelfUserBondingItem: LiveData<List<BondingData>> = _listSelfUserBondingItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun getPreferences(): CurrentStateModel {
        return currentPreferences.getCurrentState()
    }

    fun getSelfBondingItems() {
        _isLoading.value = true
        val client = ApiConfig.getApiService(currentPreferences).getSelfUser()
        client.enqueue(object : Callback<SelfDataResponse> {
            override fun onResponse(
                call: Call<SelfDataResponse>,
                response: Response<SelfDataResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("ProfileViewModel", "SUCCESSFUL")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = false
                        _isLoading.value = false
                        _responseMessage.value = responseBody.message
                        _listSelfUserBondingItem.value = responseBody.data.activities
                        Log.d("ProfileViewModel", responseBody.data.activities.size.toString())
                        _responseMessage.value = null
                    }
                } else {
                    Log.d("ProfileViewModel", "UNSUCCESSFUL")
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<SelfDataResponse>, t: Throwable) {
                Log.d("ProfileViewModel", "FAILURE")
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }
}