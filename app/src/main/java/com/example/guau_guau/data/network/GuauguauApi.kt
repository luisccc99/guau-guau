package com.example.guau_guau.data.network

import com.example.guau_guau.data.responses.LoginResponse
import com.example.guau_guau.data.responses.UserResponse
import retrofit2.http.*

interface GuauguauApi {

    @FormUrlEncoded
    @POST(value = "api/v1/login")
    suspend fun login(
        @Field(value = "email") email: String,
        @Field(value = "password") password: String
    ): LoginResponse

    @GET(value = "api/v1/user")
    suspend fun getUser(
        @Query("id") userId: String
    ): UserResponse
}
