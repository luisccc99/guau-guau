package com.example.guau_guau.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface GuauguauApi {
    companion object {
        const val BASE_URL = "https://pupperinossearch.herokuapp.com/"
    }
    //@Headers("Accept-Version: v1")
    @GET("api/v1/posts")
    suspend fun searchPosts(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ):GuauguauResponse
}