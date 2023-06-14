package com.rickyslash.travis.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignupModel (
    var name: String? = null,
    var password: String? = null,
    var email: String? = null,
    var gender: String? = null,
    var age: Int = 0,
    var travelPreferences: List<String>? = listOf(),
): Parcelable