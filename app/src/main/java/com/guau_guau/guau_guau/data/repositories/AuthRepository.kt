package com.guau_guau.guau_guau.data.repositories

import com.guau_guau.guau_guau.data.UserPreferences
import com.guau_guau.guau_guau.data.network.GuauguauApi
import java.util.*

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

    suspend fun saveExpTime(expTime: String) {
        preferences.saveExpTime(expTime)
    }
}