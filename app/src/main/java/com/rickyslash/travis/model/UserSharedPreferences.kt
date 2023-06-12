package com.rickyslash.travis.model

import android.content.Context

class UserSharedPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putStringSet(TRAVEL_PREFERENCE, value.travelPreferences)
        editor.putString(GENDER, value.gender)
        editor.putInt(AGE, value.age)
        editor.putBoolean(IS_LOGIN, value.isLogin)
        editor.putString(ACCESS_TOKEN, value.accessToken)
        editor.putString(REFRESH_TOKEN, value.refreshToken)
        editor.apply()
    }

    fun getUser(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.gender = preferences.getString(GENDER, "")
        model.age = preferences.getInt(AGE, 0)
        model.travelPreferences = preferences.getStringSet(TRAVEL_PREFERENCE, null)
        model.accessToken = preferences.getString(ACCESS_TOKEN, "")
        model.refreshToken = preferences.getString(REFRESH_TOKEN, "")
        model.isLogin = preferences.getBoolean(IS_LOGIN, false)

        return model
    }

    companion object {
        private const val PREFS_NAME = "user_preferences"
        private const val NAME = "name"
        private const val GENDER = "gender"
        private const val AGE = "age"
        private const val TRAVEL_PREFERENCE = "travel_preference"
        private const val IS_LOGIN = "isLogin"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }

}