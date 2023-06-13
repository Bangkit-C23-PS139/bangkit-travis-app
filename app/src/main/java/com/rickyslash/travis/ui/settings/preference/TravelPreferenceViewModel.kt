package com.rickyslash.travis.ui.settings.preference

import androidx.lifecycle.ViewModel
import com.rickyslash.travis.model.TravelPreferenceDataSource
import com.rickyslash.travis.model.UserSharedPreferences

class TravelPreferenceViewModel(private val userPreferences: UserSharedPreferences): ViewModel() {

    fun getSelfPreferences(): List<String> {
        return userPreferences.getUser().travelPreferences?.toList() ?: listOf()
    }

    fun getTravelPreferences(): List<String> {
        return TravelPreferenceDataSource.travelPreferenceData
    }

}