package kr.hs.entrydsm.repo.domain.common.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object EmailSuffixNotValidException : DomainCustomException(
    DomainErrorCode.EMAIL_SUFFIX_NOT_VALID
)