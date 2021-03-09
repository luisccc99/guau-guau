package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.data.network.GuauguauApi

class AuthRepository(
    private val api: GuauguauApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }

    suspend fun saveUserId(userId: String) {
        preferences.saveUserId(userId)
    }
}