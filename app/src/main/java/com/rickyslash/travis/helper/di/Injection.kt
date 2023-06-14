package com.rickyslash.travis.helper.di

import android.app.Application
import com.rickyslash.travis.model.CurrentStatePreferences

object Injection {
    fun providePreferences(application: Application): CurrentStatePreferences {
        return CurrentStatePreferences(application)
    }
}