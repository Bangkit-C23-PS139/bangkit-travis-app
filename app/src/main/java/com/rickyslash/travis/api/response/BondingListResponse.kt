package com.rickyslash.travis.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class BondingListResponse(

	@field:SerializedName("data")
	val data: List<BondingListDataItem>,

	@field:SerializedName("totalPage")
	val totalPage: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("totalActivity")
	val totalActivity: Int
)

@Parcelize
data class BondingListDataItem(

	@field:SerializedName("activity_name")
	val activityName: String,

	@field:SerializedName("start_time")
	val startTime: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: String,

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
): Parcelable
