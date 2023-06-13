package com.rickyslash.travis.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rickyslash.travis.helper.di.Injection
import com.rickyslash.travis.ui.login.LoginViewModel
import com.rickyslash.travis.ui.main.pages.bonding.BondingViewModel
import com.rickyslash.travis.ui.main.pages.menubox.MenuBoxViewModel
import com.rickyslash.travis.ui.settings.preference.TravelPreferenceViewModel

class ViewModelFactory(private val application: Application): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.providePreferences(application)) as T
            }
            modelClass.isAssignableFrom(BondingViewModel::class.java) -> {
                BondingViewModel(Injection.providePreferences(application)) as T
            }
            modelClass.isAssignableFrom(TravelPreferenceViewModel::class.java) -> {
                TravelPreferenceViewModel(Injection.providePreferences(application)) as T
            }
            modelClass.isAssignableFrom(MenuBoxViewModel::class.java) -> {
                MenuBoxViewModel(Injection.providePreferences(application)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}