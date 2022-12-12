package com.example.exit.domain.common.custom

import com.example.exit.domain.common.DomainErrorCode

abstract class DomainCustomException(
    val errorProperty: DomainErrorCode
) : RuntimeException()
