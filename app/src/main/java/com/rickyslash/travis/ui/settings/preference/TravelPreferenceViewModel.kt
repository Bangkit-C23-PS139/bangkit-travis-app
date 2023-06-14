package com.rickyslash.travis.ui.settings.preference

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.response.SignupResponse
import com.rickyslash.travis.api.response.UpdateSelfUserResponse
import com.rickyslash.travis.model.SignupModel
import com.rickyslash.travis.model.TravelPreferenceDataSource
import com.rickyslash.travis.model.CurrentStateModel
import com.rickyslash.travis.model.CurrentStatePreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravelPreferenceViewModel(private val currentPreferences: CurrentStatePreferences): ViewModel() {

    fun getSelfPreferences(): List<String> {
        return currentPreferences.getUser().travelPreferences?.toList() ?: listOf()
    }

    fun getTravelPreferences(): List<String> {
        return TravelPreferenceDataSource.travelPreferenceData
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun getPreferences(): CurrentStateModel {
        return currentPreferences.getUser()
    }

    fun setTravelPreference(newPreference: List<String>) {
        currentPreferences.setTravelPreference(newPreference.toSet())
    }

    fun updateTravelPreference(newPreference: List<String>) {
        val preferences = getPreferences()
        _isLoading.value = true

        val client = ApiConfig.getApiService(preferences.accessToken, preferences.refreshToken)
            .updateTravelPreference(newPreference)
        client.enqueue(object : Callback<UpdateSelfUserResponse> {
            override fun onResponse(
                call: Call<UpdateSelfUserResponse>,
                response: Response<UpdateSelfUserResponse>
            ) {
                Log.d("updatePreference", "ENQUEUE")
                if (response.isSuccessful) {
                    Log.d("updatePreference", "SUCCESS")
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = responseBody.message != "berhasil update detail user"
                        Log.d("updatePreference", responseBody.message)
                        setTravelPreference(newPreference)
                        _responseMessage.value = responseBody.message
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    Log.d("updatePreference", response.message())
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<UpdateSelfUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                Log.d("updatePreference", t.message.toString())
                _responseMessage.value = null
            }
        })
    }

    fun userSignup(signupData: SignupModel?) {
        _isLoading.value = true
        if (signupData != null) {
            Log.d("PreferenceSignup", "DATA NOT NULL")
            val client = ApiConfig.getApiService().userSignup(
                name = signupData.name!!,
                email = signupData.email!!,
                password = signupData.password!!,
                age = signupData.age.toString(),
                gender = signupData.gender!!,
                travelPreferences = signupData.travelPreferences!!
            )
            client.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    Log.d("signup", "ENQUEUE")
                    if (response.isSuccessful) {
                        Log.d("signup", "SUCCESS")
                        _isLoading.value = false
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _isError.value = responseBody.message != "Successfully register a new user"
                            Log.d("signup", responseBody.message)
                            _responseMessage.value = responseBody.message
                            _responseMessage.value = null
                        }
                    } else {
                        _isLoading.value = false
                        _isError.value = true
                        _responseMessage.value = response.message()
                        Log.d("signup", response.message())
                        _responseMessage.value = null
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = t.message
                    Log.d("signup", t.message.toString())
                    _responseMessage.value = null
                }
            })
        } else {
            _isLoading.value = false
            _isError.value = true
            _responseMessage.value = "Trouble retrieving signup data :("
            Log.d("signup", "Trouble retrieving signup data :(")
        }
    }
}