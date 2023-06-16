package com.rickyslash.travis.api.response

import com.google.gson.annotations.SerializedName

data class DestinationRecomResponse(

	@field:SerializedName("result")
	val result: Result
)

data class RestaurantRecommendationItem(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("map_url")
	val mapUrl: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("total_review")
	val totalReview: Any,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("keyword_category")
	val keywordCategory: String,

	@field:SerializedName("img_url")
	val imgUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("longitude")
	val longitude: Any
)

data class DestinationRecommendationItem(

	@field:SerializedName("address")
	var address: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("map_url")
	var mapUrl: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("total_review")
	val totalReview: Any,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("keyword_category")
	var keywordCategory: String,

	@field:SerializedName("img_url")
	var imgUrl: String,

	@field:SerializedName("name")
	var name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("longitude")
	val longitude: Any
)

data class HotelRecommendationItem(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("map_url")
	val mapUrl: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("total_review")
	val totalReview: Any,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("keyword_category")
	val keywordCategory: String,

	@field:SerializedName("img_url")
	val imgUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("longitude")
	val longitude: Any
)

data class Result(

	@field:SerializedName("hotel_recommendation")
	val hotelRecommendation: List<HotelRecommendationItem>,

	@field:SerializedName("destination_recommendation")
	val destinationRecommendation: List<DestinationRecommendationItem>,

	@field:SerializedName("restaurant_recommendation")
	val restaurantRecommendation: List<RestaurantRecommendationItem>
)
