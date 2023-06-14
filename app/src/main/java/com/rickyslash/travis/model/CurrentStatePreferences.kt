package com.rickyslash.travis.model

import android.content.Context

class CurrentStatePreferences(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setCurrentState(value: CurrentStateModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putStringSet(TRAVEL_PREFERENCE, value.travelPreferences)
        editor.putString(CURRENT_LOCATION, value.currentLocation)
        editor.putString(PROFILE_PHOTO, value.profilePhoto)
        editor.putBoolean(IS_LOGIN, value.isLogin)
        editor.putString(ACCESS_TOKEN, value.accessToken)
        editor.putString(REFRESH_TOKEN, value.refreshToken)
        editor.apply()
    }

    fun getCurrentState(): CurrentStateModel {
        val model = CurrentStateModel()
        model.name = preferences.getString(NAME, "")
        model.travelPreferences = preferences.getStringSet(TRAVEL_PREFERENCE, setOf())
        model.currentLocation = preferences.getString(CURRENT_LOCATION, "")
        model.profilePhoto = preferences.getString(PROFILE_PHOTO, "")
        model.accessToken = preferences.getString(ACCESS_TOKEN, "")
        model.refreshToken = preferences.getString(REFRESH_TOKEN, "")
        model.isLogin = preferences.getBoolean(IS_LOGIN, false)

        return model
    }

    fun setTravelPreference(newPreference: Set<String>) {
        val editor = preferences.edit()
        editor.putStringSet(TRAVEL_PREFERENCE, newPreference)
        editor.apply()
    }

    fun setCurrentLocation(loc: String) {
        val editor = preferences.edit()
        editor.putString(CURRENT_LOCATION, loc)
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "user_preferences"
        private const val NAME = "name"
        private const val CURRENT_LOCATION = "current_location"
        private const val PROFILE_PHOTO = "profile_photo"
        private const val TRAVEL_PREFERENCE = "travel_preference"
        private const val IS_LOGIN = "isLogin"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }

}