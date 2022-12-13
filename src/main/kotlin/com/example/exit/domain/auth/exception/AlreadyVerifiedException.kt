package com.example.exit.domain.auth.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object AlreadyVerifiedException : DomainCustomException(
    DomainErrorCode.ALREADY_VERIFIED_EXCEPTION
)