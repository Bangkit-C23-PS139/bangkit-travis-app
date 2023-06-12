package com.rickyslash.travis.api.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rickyslash.travis.api.response.UsersItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BondingData(
    @field:SerializedName("activity_name")
    val bondingName: String,

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

    @field:SerializedName("users")
    val users: @RawValue List<UsersItem>,

    @field:SerializedName("updatedAt")
    val updatedAt: String
): Parcelable
