package com.rickyslash.travis.ui.main.pages.menubox

import androidx.lifecycle.ViewModel
import com.rickyslash.travis.model.UserModel
import com.rickyslash.travis.model.UserSharedPreferences

class MenuBoxViewModel(private val userPreferences: UserSharedPreferences): ViewModel() {

    fun logout() {
        userPreferences.setUser(UserModel())
    }

}