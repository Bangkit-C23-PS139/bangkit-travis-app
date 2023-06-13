package com.rickyslash.travis.api.response

import com.google.gson.annotations.SerializedName
import com.rickyslash.travis.api.entity.SelfData

data class SelfDataResponse(

	@field:SerializedName("data")
	val data: SelfData,

	@field:SerializedName("message")
	val message: String
)
