package com.rickyslash.travis.ui.main.pages.bonding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.response.BondingDataItem
import com.rickyslash.travis.api.response.BondingListDataItem
import com.rickyslash.travis.api.response.BondingListResponse
import com.rickyslash.travis.api.response.LinkToBondingResponse
import com.rickyslash.travis.data.TravelRepository
import com.rickyslash.travis.helper.getFirstWord
import com.rickyslash.travis.model.CurrentStateModel
import com.rickyslash.travis.model.CurrentStatePreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BondingViewModel(private val currentPreferences: CurrentStatePreferences, travelRepository: TravelRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    private val _joinResponseMessage = MutableLiveData<String?>()
    val joinResponseMessage: LiveData<String?> = _joinResponseMessage

    fun getPreferences(): CurrentStateModel {
        return currentPreferences.getCurrentState()
    }

    val bonding: LiveData<PagingData<BondingDataItem>> =
        travelRepository.getBonding(getPreferences().currentLocation!!).cachedIn(viewModelScope)

    fun joinBonding(bondingId: String) {
        val client = ApiConfig.getApiService(currentPreferences).linkToBonding(bondingId)
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

    fun unlinkBonding(bondingId: String) {
        val client = ApiConfig.getApiService(currentPreferences).unlinkBonding(bondingId)
        client.enqueue(object : Callback<LinkToBondingResponse> {
            override fun onResponse(
                call: Call<LinkToBondingResponse>,
                response: Response<LinkToBondingResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("UnlinkBonding", "Successful ${responseBody.message}")
                    }
                } else {
                    Log.d("UnlinkBonding", "NOT Successful ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LinkToBondingResponse>, t: Throwable) {
                Log.d("UnlinkBonding", "Failure ${t.message}")
            }
        })
    }
    
}