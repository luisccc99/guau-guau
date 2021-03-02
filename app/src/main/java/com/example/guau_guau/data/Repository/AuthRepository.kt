package com.example.guau_guau.data.Repository

import com.example.guau_guau.data.Network.AuthAPI

class AuthRepository(
    private val api: AuthAPI
) : BaseRepository(){

    suspend fun login (
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }
}