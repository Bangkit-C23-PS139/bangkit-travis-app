package com.rickyslash.travis.api.response

import com.google.gson.annotations.SerializedName

data class SignupResponse(

	@field:SerializedName("data")
	val data: SignupData,

	@field:SerializedName("message")
	val message: String
)

data class SignupData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: Any,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("picture_url")
	val pictureUrl: Any,

	@field:SerializedName("travel_preferences")
	val travelPreferences: List<String>,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("age")
	val age: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
