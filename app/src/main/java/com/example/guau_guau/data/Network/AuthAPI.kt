package com.example.guau_guau.data.Network

import com.example.guau_guau.data.Responses.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthAPI {

    @FormUrlEncoded
    @POST( value = "api/v1/login")
    suspend fun login(
        @Field ( value="email") email: String,
        @Field ( value="password") password: String
    ) : LoginResponse
}
