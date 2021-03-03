package com.example.guau_guau.data.Responses

data class LoginResponse(
    val token: String,
    val message: String,
    val exp: String
)