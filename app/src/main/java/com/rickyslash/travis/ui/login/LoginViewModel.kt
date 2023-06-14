package com.rickyslash.travis.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.api.response.LoginResponse
import com.rickyslash.travis.api.response.SelfDataResponse
import com.rickyslash.travis.model.UserModel
import com.rickyslash.travis.model.UserSharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val userPreferences: UserSharedPreferences): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

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
                        userLoginSetData(
                            responseBody.userStatus.tokenData.accessToken,
                            responseBody.userStatus.tokenData.refreshToken
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

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }

    fun userLoginSetData(accessToken: String, refreshToken: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(accessToken, refreshToken).getSelfUser()
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
                        userPreferences.setUser(
                            UserModel(
                                name = responseBody.data.nama,
                                gender = responseBody.data.gender,
                                age = responseBody.data.age,
                                travelPreferences = responseBody.data.travelPreferences.toMutableSet(),
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                                isLogin = true
                            )
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