package com.example.exit.domain.common.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException
import com.example.exit.global.error.GlobalErrorCode
import com.example.exit.global.error.custom.GlobalCustomException

object PasswordMissMatchedException : DomainCustomException(
    DomainErrorCode.PASSWORD_MISS_MATCHED
)