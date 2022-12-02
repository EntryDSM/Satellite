package com.example.exit.global.error.response

data class DefaultErrorResponse(
    val status: Int,
    val message: String,
    val code: String
)