package kr.hs.entrydsm.satellite.domain.school.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object SecretMismatchException : DomainCustomException(
    DomainErrorCode.SECRET_MISMATCH
)