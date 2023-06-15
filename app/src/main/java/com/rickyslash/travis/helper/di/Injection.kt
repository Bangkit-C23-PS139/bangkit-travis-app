package com.rickyslash.travis.helper.di

import android.app.Application
import android.content.Context
import com.rickyslash.travis.api.ApiConfig
import com.rickyslash.travis.data.TravelRepository
import com.rickyslash.travis.model.CurrentStatePreferences

object Injection {
    fun provideRepository(context: Context): TravelRepository {
        val currentStatePreferences = CurrentStatePreferences(context)
        val apiService = ApiConfig.getApiService(currentStatePreferences)
        return TravelRepository(apiService)
    }
    fun providePreferences(application: Application): CurrentStatePreferences {
        return CurrentStatePreferences(application)
    }
}