package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.AuthAPI

class AuthRepository(
    private val api: AuthAPI
) : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }
}