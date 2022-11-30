package com.example.exit.domain.auth.dto.request

data class TokenResponse (
    val accessToken: String,
    val refreshToken: String
)