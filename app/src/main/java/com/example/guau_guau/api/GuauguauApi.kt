package com.example.guau_guau.api

import com.example.guau_guau.data.GuauguauPost
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface GuauguauApi {
    companion object {
        const val BASE_URL = "https://pupperinossearch.herokuapp.com/"
    }

    @GET("api/v1/posts")
    suspend fun searchPosts(
        @Query("resolved") page: Boolean,
        @Query("page") per_page: Int?,
        @Header("Authorization") token: String
    ):List<GuauguauPost>
}