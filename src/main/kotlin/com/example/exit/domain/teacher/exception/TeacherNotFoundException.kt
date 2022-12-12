package com.example.exit.domain.teacher.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object TeacherNotFoundException : DomainCustomException(
    DomainErrorCode.TEACHER_NOT_FOUND
)