package com.rickyslash.travis.ui.main

import androidx.lifecycle.ViewModel
import com.rickyslash.travis.model.CurrentStatePreferences

class MainViewModel(private val currentPreferences: CurrentStatePreferences): ViewModel() {

    fun setCurrentLocation(loc: String) {
        currentPreferences.setCurrentLocation(loc)
    }

}