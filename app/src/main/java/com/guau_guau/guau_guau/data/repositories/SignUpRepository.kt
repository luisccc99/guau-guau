package com.guau_guau.guau_guau.data.repositories

import com.guau_guau.guau_guau.data.network.GuauguauApi

class SignUpRepository(
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun signup(
        name: String,
        lastname: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.signUp(name, lastname, email, password)
    }
}