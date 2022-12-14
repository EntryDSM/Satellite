package com.example.exit.domain.auth.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object IsNotVerifiedPhoneNumber : DomainCustomException(
    DomainErrorCode.IS_NOT_VERIFIED_PHONE_NUMBER
)