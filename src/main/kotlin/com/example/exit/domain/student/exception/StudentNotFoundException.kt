package com.example.exit.domain.student.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object StudentNotFoundException : DomainCustomException(
    DomainErrorCode.STUDENT_NOT_FOUND
)