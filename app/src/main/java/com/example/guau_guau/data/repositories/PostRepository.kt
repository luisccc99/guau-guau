package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi

class PostRepository (
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun postSubmit(id: String, title: String, body: String) = safeApiCall {
        api.PostSubmit(id, title, body)
    }

}