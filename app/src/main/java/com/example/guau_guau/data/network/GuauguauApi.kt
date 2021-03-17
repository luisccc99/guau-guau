package com.example.guau_guau.data.network

import com.example.guau_guau.data.responses.LoginResponse
import com.example.guau_guau.data.responses.UserResponse
import retrofit2.http.*

interface GuauguauApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field(value = "email") email: String,
        @Field(value = "password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST(value = "users")
    suspend fun signup(
        @Field("name") name: String,
        @Field("lastname") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): UserResponse

    @GET("user")
    suspend fun getUser(
        @Query("id") userId: String
    ): UserResponse
}
