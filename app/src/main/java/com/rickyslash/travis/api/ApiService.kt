package com.rickyslash.travis.api

import com.rickyslash.travis.api.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/auth/login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/auth/register")
    fun userSignup(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("age") age: String,
        @Field("gender") gender: String,
        @Field("travel_preferences[]") travelPreferences: List<String>,
        @Field("picture_url") profilePhoto: String
    ): Call<SignupResponse>

    @FormUrlEncoded
    @PUT("/users/self")
    fun updateTravelPreference(
        @Field("travel_preferences[]") travelPreferences: List<String>
    ): Call<UpdateSelfUserResponse>

    @GET("/users/self")
    fun getSelfUser(): Call<SelfDataResponse>

    @POST("/activity/{activity_id}/link")
    fun linkToBonding(
        @Path("activity_id") bondingId: String
    ): Call<LinkToBondingResponse>

    @GET("/activity")
    fun getBondingList(): Call<BondingListResponse>

    @GET("/destination")
    suspend fun getHighlight(
        @Query("page") page: Int = 0,
        @Query("pagesize") size: Int = 5,
        @Query("city") city: String
    ): HighlightResponse

}