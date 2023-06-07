package com.rickyslash.travis.api

import com.rickyslash.travis.api.response.DummyHighlightResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/v1/stories")
    fun getHighlights(): Call<DummyHighlightResponse>

}