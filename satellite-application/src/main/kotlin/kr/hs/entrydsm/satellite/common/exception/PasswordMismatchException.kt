package kr.hs.entrydsm.satellite.common.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object PasswordMismatchException : DomainCustomException(
    DomainErrorCode.PASSWORD_MISMATCH
)