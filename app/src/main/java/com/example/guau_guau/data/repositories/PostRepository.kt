package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi

class PostRepository (
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun createPost(id: String, title: String, body: String) = safeApiCall {
        api.createPost(id, title, body)
    }

    suspend fun deletePost() = safeApiCall {

    }

}