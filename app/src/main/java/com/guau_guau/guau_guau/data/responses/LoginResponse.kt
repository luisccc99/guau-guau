package com.guau_guau.guau_guau.data.responses

data class LoginResponse(
    val user_id: String,
    val token: String,
    val message: String,
    val exp: String
)