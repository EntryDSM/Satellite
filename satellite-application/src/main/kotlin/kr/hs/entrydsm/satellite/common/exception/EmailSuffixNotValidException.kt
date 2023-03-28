package kr.hs.entrydsm.satellite.common.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object EmailSuffixNotValidException : DomainCustomException(
    DomainErrorCode.EMAIL_SUFFIX_NOT_VALID
)