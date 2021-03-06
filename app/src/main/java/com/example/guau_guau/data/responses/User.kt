package com.example.guau_guau.data.responses

data class User(
    val created_at: String,
    val email: String,
    val id: String,
    val lastname: String,
    val name: String,
    val num_posts: Int,
    val password_digest: String,
    val photo: Any,
    val resolved_posts: Int,
    val updated_at: String,
    val token: String?
)