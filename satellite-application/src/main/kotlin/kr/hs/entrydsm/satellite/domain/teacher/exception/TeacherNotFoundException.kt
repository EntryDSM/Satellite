package kr.hs.entrydsm.satellite.domain.teacher.exception

import kr.hs.entrydsm.satellite.common.exception.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object TeacherNotFoundException : DomainCustomException(
    DomainErrorCode.TEACHER_NOT_FOUND
)