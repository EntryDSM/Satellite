package kr.hs.entrydsm.exit.domain.document.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object IllegalStatusException : DomainCustomException(
    DomainErrorCode.ILLEGAL_STATUS
)
