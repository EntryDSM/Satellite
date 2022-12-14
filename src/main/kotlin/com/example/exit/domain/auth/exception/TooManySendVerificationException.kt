package com.example.exit.domain.auth.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object TooManySendVerificationException : DomainCustomException(
    DomainErrorCode.TOO_MANY_SEND_VERIFICATION_CODE
)