package com.rickyslash.travis.api.entity

import com.google.gson.annotations.SerializedName

data class SelfData(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("deletedAt")
    val deletedAt: Any,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("picture_url")
    val pictureUrl: String,

    @field:SerializedName("travel_preferences")
    val travelPreferences: List<String>,

    @field:SerializedName("activities")
    val activities: List<Any>,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("age")
    val age: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)
