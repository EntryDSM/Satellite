package kr.hs.entrydsm.repo.domain.document.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object IllegalStatusException : DomainCustomException(
    DomainErrorCode.ILLEGAL_STATUS
)
