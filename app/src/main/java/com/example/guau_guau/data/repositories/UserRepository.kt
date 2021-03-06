package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.UserApi


class UserRepository (
    private val api: UserApi
    ) : BaseRepository() {

        suspend fun getUser() = safeApiCall {
            api.getUser()
        }
}