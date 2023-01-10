package kr.hs.entrydsm.exit.domain.student.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object StudentAlreadyExistException : DomainCustomException(
    DomainErrorCode.STUDENT_ALREADY_EXIST
)