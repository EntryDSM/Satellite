package com.example.exit.domain.common.error

import com.example.exit.domain.common.DomainErrorCode

abstract class DomainCustomException(
    val errorProperty: DomainErrorCode
) : RuntimeException()
