package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi

class PostRepository(
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun createPost(id: String, title: String, body: String, latitude: Double?, longitude: Double?) = safeApiCall {
        api.createPost(id, title, body, longitude, latitude)
    }

    suspend fun deletePost(postId: String) = safeApiCall {
        api.deletePost(postId)
    }

    suspend fun patchPost(
        postId: String,
        resolved: Boolean,
        resolvedReason: String
    ) = safeApiCall {
        api.patchPost(postId, resolved, resolvedReason)
    }

    suspend fun getPost(
        postId: String
    ) = safeApiCall {
        api.getPost(postId)
    }

    suspend fun getUserById(
        userId: String
    ) = safeApiCall {
        api.getUser(userId)
    }

}