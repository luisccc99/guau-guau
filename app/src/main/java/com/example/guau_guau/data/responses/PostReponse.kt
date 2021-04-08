package com.example.guau_guau.data.responses

data class PostReponse(
    val body: String,
    val created_at: String,
    val id: String,
    val photo: Photo,
    val resolved: Boolean,
    val resolved_reason: String,
    val title: String,
    val updated_at: String,
    val user_id: String
)