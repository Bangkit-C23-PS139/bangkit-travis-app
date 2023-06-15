package com.rickyslash.travis.api.response

import com.google.gson.annotations.SerializedName

data class BondingResponse(

	@field:SerializedName("data")
	val data: List<BondingDataItem>,

	@field:SerializedName("totalPage")
	val totalPage: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("totalActivity")
	val totalActivity: Int
)

data class BondingDataItem(

	@field:SerializedName("activity_name")
	val activityName: String,

	@field:SerializedName("start_time")
	val startTime: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: Any,

	@field:SerializedName("gmap_link")
	val gmapLink: String,

	@field:SerializedName("background_img")
	val backgroundImg: String,

	@field:SerializedName("end_time")
	val endTime: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
