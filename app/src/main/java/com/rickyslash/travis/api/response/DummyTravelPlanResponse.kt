package com.rickyslash.travis.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DummyTravelPlanResponse (

    @field:SerializedName("listStory")
    val listTravelPlan: List<TravelPlanItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)

@Parcelize
data class TravelPlanItem(

    @field:SerializedName("photoUrl")
    var photoUrl: String?,

    @field:SerializedName("createdAt")
    var createdAt: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("description")
    var description: String,

    @field:SerializedName("lon")
    val lon: Float,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Float
): Parcelable