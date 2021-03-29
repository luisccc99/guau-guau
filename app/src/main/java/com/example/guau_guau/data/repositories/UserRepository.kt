package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi


class UserRepository(
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun getUser(userId: String) = safeApiCall {
        api.getUser(userId)
    }

    suspend fun editUserAbout(id: String, aboutMe: String) = safeApiCall {
        api.patchUserAbout(id, aboutMe)
    }

    suspend fun editUserNameAndLastName(id: String, name: String, lastname: String) = safeApiCall {
        api.patchUserNameAndLastName(id, name, lastname)
    }
}