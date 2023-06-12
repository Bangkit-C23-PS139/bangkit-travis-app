package com.rickyslash.travis.api

import com.rickyslash.travis.api.response.BondingListResponse
import com.rickyslash.travis.api.response.LinkToBondingResponse
import com.rickyslash.travis.api.response.LoginResponse
import com.rickyslash.travis.api.response.SelfDataResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/auth/login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("/users/self")
    fun getSelfUser(): Call<SelfDataResponse>

    @POST("/activity/{activity_id}/link")
    fun linkToBonding(
        @Path("activity_id") bondingId: String
    ): Call<LinkToBondingResponse>

    @GET("/activity")
    fun getBondingList(): Call<BondingListResponse>

}