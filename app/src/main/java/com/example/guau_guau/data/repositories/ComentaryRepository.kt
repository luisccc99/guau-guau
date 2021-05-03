package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi
import javax.inject.Inject
import javax.inject.Singleton

class ComentaryRepository (
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun ComentarySubmit(id: String, body: String) = safeApiCall {

    }
}