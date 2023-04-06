package kr.hs.entrydsm.satellite.domain.student.exception

import kr.hs.entrydsm.satellite.common.exception.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object StudentAlreadyExistException : DomainCustomException(
    DomainErrorCode.STUDENT_ALREADY_EXIST
)