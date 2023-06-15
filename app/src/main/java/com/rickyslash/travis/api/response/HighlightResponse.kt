package com.rickyslash.travis.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HighlightResponse(

	@field:SerializedName("data")
	val data: HighlightData,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class HighlightDataItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: String,

	@field:SerializedName("activity")
	val activity: List<String>,

	@field:SerializedName("gmap_link")
	val gmapLink: String,

	@field:SerializedName("background_img")
	val backgroundImg: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("destination_name")
	val destinationName: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("contact_number")
	val contactNumber: String,

	@field:SerializedName("image_gallery")
	val imageGallery: List<String>,

	@field:SerializedName("updatedAt")
	val updatedAt: String
): Parcelable

data class HighlightData(

	@field:SerializedName("data")
	val data: List<HighlightDataItem>,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("page")
	val page: Int
)
