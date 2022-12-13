package com.example.exit.domain.auth.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object VerificationCodeMisMatchedException : DomainCustomException(
    DomainErrorCode.VERIFICATION_CODE_MISS_MATCHED
)