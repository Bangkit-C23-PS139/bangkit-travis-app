package com.rickyslash.travis.api

import com.rickyslash.travis.api.response.DummyBondingResponse
import com.rickyslash.travis.api.response.DummyHighlightResponse
import com.rickyslash.travis.api.response.DummyServiceResponse
import com.rickyslash.travis.api.response.DummyTravelPlanResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

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