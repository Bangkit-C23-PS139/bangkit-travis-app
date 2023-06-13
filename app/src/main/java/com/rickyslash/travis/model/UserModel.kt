package com.rickyslash.travis.model

data class UserModel (
    var name: String? = null,
    var gender: String? = null,
    var age: Int = 0,
    var travelPreferences: Set<String>? = setOf(),
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var isLogin: Boolean = false
)