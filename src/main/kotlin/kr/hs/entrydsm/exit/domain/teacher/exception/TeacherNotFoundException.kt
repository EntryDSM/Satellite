package kr.hs.entrydsm.exit.domain.teacher.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object TeacherNotFoundException : DomainCustomException(
    DomainErrorCode.TEACHER_NOT_FOUND
)