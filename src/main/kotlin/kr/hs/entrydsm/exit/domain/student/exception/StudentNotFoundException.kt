package kr.hs.entrydsm.exit.domain.student.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object StudentNotFoundException : DomainCustomException(
    DomainErrorCode.STUDENT_NOT_FOUND
)