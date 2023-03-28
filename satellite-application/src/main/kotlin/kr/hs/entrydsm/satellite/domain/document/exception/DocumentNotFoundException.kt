package kr.hs.entrydsm.satellite.domain.document.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object DocumentNotFoundException : DomainCustomException(
    DomainErrorCode.DOCUMENT_NOT_FOUND
)