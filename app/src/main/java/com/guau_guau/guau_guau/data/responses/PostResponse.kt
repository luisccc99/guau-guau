package com.guau_guau.guau_guau.data.responses

data class PostResponse(
    val body: String,
    val created_at: String,
    val id: String,
    val photo: Photo,
    val resolved: Boolean,
    val resolved_reason: String,
    val longitude: Double,
    val latitude: Double,
    val title: String,
    val updated_at: String,
    val user_id: String,
    val message: String?
)