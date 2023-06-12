package com.rickyslash.travis.api.dummy

import com.rickyslash.travis.api.dummy.dummyresponse.*
import com.rickyslash.travis.api.response.*
import retrofit2.Call
import retrofit2.http.*

interface DummyApiService {

    @FormUrlEncoded
    @POST("/v1//login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<DummyLoginResponse>

    @GET("/v1/stories")
    fun getHighlights(): Call<DummyHighlightResponse>

    @GET("/v1/stories")
    fun getTravelPlan(
        @Query("size") size: Int = 6
    ): Call<DummyTravelPlanResponse>

    @GET("/v1/stories")
    fun getServices(): Call<DummyServiceResponse>

    @GET("/v1/stories")
    fun getBondingList(): Call<DummyBondingResponse>

}