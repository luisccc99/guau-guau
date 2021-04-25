package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun getUser(userId: String) = safeApiCall {
        api.getUser(userId)
    }

    suspend fun patchUser(id: String, name: String?, lastname: String?, about: String?) = safeApiCall {
        api.patchUser(id, name, lastname, about)
    }
}