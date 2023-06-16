package com.rickyslash.travis.api

import androidx.viewbinding.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MLApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)

            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeader = req.newBuilder()
                    .build()
                chain.proceed(requestHeader)
            }
            client.addInterceptor(authInterceptor)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://travis-ml-atwlfi6ica-as.a.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}