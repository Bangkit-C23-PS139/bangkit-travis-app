package com.rickyslash.travis.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.response.LoginResponse
import com.rickyslash.travis.api.response.SelfDataResponse
import com.rickyslash.travis.model.CurrentStatePreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val currentPreferences: CurrentStatePreferences): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun setTokens(accessToken: String, refreshToken: String) {
        currentPreferences.setTokens(accessToken, refreshToken)
    }

    fun userLogin(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = responseBody.message != "berhasil melakukan autentikasi"
                        _responseMessage.value = responseBody.message
                        setTokens(
                            responseBody.userStatus.tokenData.accessToken,
                            responseBody.userStatus.tokenData.refreshToken
                        )
                        _responseMessage.value = null
                        userLoginSetData()
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }

    fun userLoginSetData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService(currentPreferences).getSelfUser()
        client.enqueue(object : Callback<SelfDataResponse> {
            override fun onResponse(
                call: Call<SelfDataResponse>,
                response: Response<SelfDataResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isError.value = responseBody.message != "Berhasil menemukan user"
                        _responseMessage.value = responseBody.message
                        currentPreferences.setUserDetails(
                            name = responseBody.data.nama,
                            travelPreferences = responseBody.data.travelPreferences.toMutableSet(),
                            profilePhoto = responseBody.data.pictureUrl,
                            isLogin = true
                        )
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<SelfDataResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }
}