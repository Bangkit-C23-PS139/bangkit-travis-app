package com.rickyslash.travis.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("userStatus")
	val userStatus: UserStatus,

	@field:SerializedName("message")
	val message: String
)

data class UserData(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("email")
	val email: String
)

data class TokenData(

	@field:SerializedName("accessTokenExpiryTime")
	val accessTokenExpiryTime: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)

data class UserStatus(

	@field:SerializedName("userData")
	val userData: UserData,

	@field:SerializedName("tokenData")
	val tokenData: TokenData
)
