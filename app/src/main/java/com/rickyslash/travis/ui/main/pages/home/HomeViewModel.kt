package com.rickyslash.travis.ui.main.pages.home

import androidx.lifecycle.ViewModel
import com.rickyslash.travis.model.CurrentStateModel
import com.rickyslash.travis.model.CurrentStatePreferences

class HomeViewModel(private val currentPreferences: CurrentStatePreferences): ViewModel() {

    fun getPreferences(): CurrentStateModel {
        return currentPreferences.getCurrentState()
    }

}