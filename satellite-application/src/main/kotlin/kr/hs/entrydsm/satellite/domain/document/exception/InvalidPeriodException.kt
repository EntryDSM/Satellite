package kr.hs.entrydsm.satellite.domain.document.exception

import kr.hs.entrydsm.satellite.common.error.DomainCustomException
import kr.hs.entrydsm.satellite.common.exception.DomainErrorCode

object InvalidPeriodException : DomainCustomException(
    DomainErrorCode.DOCUMENT_ACCESS_RIGHT
)