package com.guau_guau.guau_guau.data.responses

data class CommentResponse(
    val body: String,
    val created_at: String,
    val id: String,
    val post_id: String,
    val updated_at: String,
    val user_id: String
)