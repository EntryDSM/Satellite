package kr.hs.entrydsm.satellite.domain.document.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object DocumentIllegalStatusException : DomainCustomException(
    DomainErrorCode.DOCUMENT_ILLEGAL_STATUS
)