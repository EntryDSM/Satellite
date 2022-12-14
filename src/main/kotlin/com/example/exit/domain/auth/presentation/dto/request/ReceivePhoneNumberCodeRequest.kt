package com.example.exit.domain.auth.presentation.dto.request

data class ReceivePhoneNumberCodeRequest(
    val phoneNumber: String,
    val code: String
)