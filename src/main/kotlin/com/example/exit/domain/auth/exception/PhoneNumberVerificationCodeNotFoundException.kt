package com.example.exit.domain.auth.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object PhoneNumberVerificationCodeNotFoundException : DomainCustomException(
    DomainErrorCode.PHONE_NUMBER_VERIFICATION_CODE_NOT_FOUND
)