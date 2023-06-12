package com.rickyslash.travis.helper.di

import android.app.Application
import com.rickyslash.travis.model.UserSharedPreferences

object Injection {
    fun providePreferences(application: Application): UserSharedPreferences {
        return UserSharedPreferences(application)
    }
}