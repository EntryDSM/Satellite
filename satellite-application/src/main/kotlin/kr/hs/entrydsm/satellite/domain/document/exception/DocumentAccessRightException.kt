package kr.hs.entrydsm.satellite.domain.document.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object DocumentAccessRightException : DomainCustomException(
    DomainErrorCode.DOCUMENT_ACCESS_RIGHT
)