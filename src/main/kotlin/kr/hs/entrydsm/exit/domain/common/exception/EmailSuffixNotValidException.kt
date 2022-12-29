package kr.hs.entrydsm.exit.domain.common.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object EmailSuffixNotValidException : DomainCustomException(
    DomainErrorCode.EMAIL_SUFFIX_NOT_VALID
)