package kr.hs.entrydsm.satellite.domain.document.exception

import kr.hs.entrydsm.satellite.common.exception.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object MajorNotFoundException : DomainCustomException(
    DomainErrorCode.MAJOR_NOT_FOUND
)