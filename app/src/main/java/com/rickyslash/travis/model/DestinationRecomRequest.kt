package com.rickyslash.travis.model

import com.google.gson.annotations.SerializedName

data class DestinationRecomRequest(
    @field:SerializedName("city")
    val city: String,
    @field:SerializedName("user_destination_preferences")
    val userDestinationPreferences: List<String>,
    @field:SerializedName("user_restaurant_preferences")
    val userRestaurantPreferences: List<String>
)
