package com.rickyslash.travis.api.response

import com.google.gson.annotations.SerializedName

data class ServiceResponse(

	@field:SerializedName("data")
	val data: ServiceData,

	@field:SerializedName("message")
	val message: String
)

data class ServiceData(

	@field:SerializedName("data")
	val data: List<ServiceDataItem>,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("totalPages")
	val totalPages: Any,

	@field:SerializedName("page")
	val page: Int
)

data class ServiceDataItem(

	@field:SerializedName("service_price")
	val servicePrice: Int,

	@field:SerializedName("service_name")
	val serviceName: String,

	@field:SerializedName("background_img")
	val backgroundImg: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("service_provider")
	val serviceProvider: String,

	@field:SerializedName("contact_number")
	val contactNumber: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: Any,

	@field:SerializedName("gmap_link")
	val gmapLink: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("image_gallery")
	val imageGallery: List<String>,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
