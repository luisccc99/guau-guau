package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi

class ComentaryRepository (
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun ComentarySubmit(id: String, body: String) = safeApiCall {
        api.ComentarySubmit(id, body)
    }
}