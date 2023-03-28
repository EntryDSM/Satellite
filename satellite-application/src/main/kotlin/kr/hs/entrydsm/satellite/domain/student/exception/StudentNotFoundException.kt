package kr.hs.entrydsm.satellite.domain.student.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object StudentNotFoundException : DomainCustomException(
    DomainErrorCode.STUDENT_NOT_FOUND
)