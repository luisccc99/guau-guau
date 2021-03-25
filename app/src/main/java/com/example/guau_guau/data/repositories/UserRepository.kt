package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi


class UserRepository(
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun getUser(userId: String) = safeApiCall {
        api.getUser(userId)
    }
    suspend fun editUser(id: String, name: String,lastName: String, aboutMy: String) = safeApiCall{
    api.patchUser(id, name, lastName, aboutMy)

    }

}