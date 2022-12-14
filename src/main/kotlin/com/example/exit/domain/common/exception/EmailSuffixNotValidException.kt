package com.example.exit.domain.common.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object EmailSuffixNotValidException : DomainCustomException(
    DomainErrorCode.EMAIL_SUFFIX_NOT_VALID
)