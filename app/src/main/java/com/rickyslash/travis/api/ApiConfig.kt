package com.rickyslash.travis.api

import androidx.viewbinding.BuildConfig
import com.rickyslash.travis.model.CurrentStatePreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(currentPreferences: CurrentStatePreferences? = null): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)

            var accessToken: String? = null
            var refreshToken: String? = null

            if (currentPreferences != null) {
                accessToken = currentPreferences.getCurrentState().accessToken
                refreshToken = currentPreferences.getCurrentState().refreshToken
            }

            if (accessToken != null && refreshToken != null) {
                val authInterceptor = Interceptor { chain ->
                    val req = chain.request()
                    val requestHeader = req.newBuilder()
                        .addHeader("Access_Token", accessToken)
                        .addHeader("Refresh_Token", refreshToken)
                        .build()
                    chain.proceed(requestHeader)
                }
                client.addInterceptor(authInterceptor)
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("http://34.142.198.169")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}