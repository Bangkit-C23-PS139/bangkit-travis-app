package com.rickyslash.travis.ui.main.pages.menubox

import androidx.lifecycle.ViewModel
import com.rickyslash.travis.model.CurrentStateModel
import com.rickyslash.travis.model.CurrentStatePreferences

class MenuBoxViewModel(private val currentPreferences: CurrentStatePreferences): ViewModel() {

    fun getPreferences(): CurrentStateModel {
        return currentPreferences.getCurrentState()
    }

    fun logout() {
        currentPreferences.setCurrentState(CurrentStateModel(
            currentLocation = getPreferences().currentLocation
        ))
    }

}