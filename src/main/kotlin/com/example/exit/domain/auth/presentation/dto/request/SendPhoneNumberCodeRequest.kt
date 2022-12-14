package com.example.exit.domain.auth.presentation.dto.request

import org.hibernate.validator.constraints.Length

data class SendPhoneNumberCodeRequest(
    @field:Length(min = 10, max = 11)
    val phoneNumber: String
)