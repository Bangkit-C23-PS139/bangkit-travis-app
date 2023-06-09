package com.rickyslash.travis.api.dummy.dummyresponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DummyHighlightResponse (

    @field:SerializedName("listStory")
    val listHighlight: List<HighlightItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)

@Parcelize
data class HighlightItem(

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