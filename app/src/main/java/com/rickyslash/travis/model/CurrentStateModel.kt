package com.rickyslash.travis.model

data class CurrentStateModel (
    var name: String? = null,
    var travelPreferences: Set<String>? = setOf(),
    var currentLocation: String? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var isLogin: Boolean = false
)