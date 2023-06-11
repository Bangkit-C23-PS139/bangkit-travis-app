package com.rickyslash.travis.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DummyServiceResponse (

    @field:SerializedName("listStory")
    val listService: List<ServiceItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)

@Parcelize
data class ServiceItem(
    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Float,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Float
): Parcelable