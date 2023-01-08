package kr.hs.entrydsm.exit.domain.document.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object MajorNotFoundException: DomainCustomException(
    DomainErrorCode.MAJOR_NOT_FOUND
)