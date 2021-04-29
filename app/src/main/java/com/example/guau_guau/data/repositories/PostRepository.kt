package com.example.guau_guau.data.repositories

import com.example.guau_guau.data.network.GuauguauApi

class PostRepository(
    private val api: GuauguauApi
) : BaseRepository() {

    suspend fun createPost(id: String, title: String, body: String) = safeApiCall {
        api.createPost(id, title, body)
    }

    suspend fun deletePost(postId: String, userId: String) = safeApiCall {
        api.deletePost(postId, userId)
    }

    suspend fun patchPost(
        postId: String,
        userId: String,
        resolved: Boolean,
        resolvedReason: String
    ) = safeApiCall {
        api.patchPost(postId, userId, resolved, resolvedReason)
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