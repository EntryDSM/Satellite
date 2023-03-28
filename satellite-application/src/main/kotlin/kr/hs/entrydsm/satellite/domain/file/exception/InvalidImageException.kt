package kr.hs.entrydsm.satellite.domain.file.exception

import kr.hs.entrydsm.satellite.common.DomainErrorCode
import kr.hs.entrydsm.satellite.common.error.DomainCustomException

object InvalidImageException : DomainCustomException(
    DomainErrorCode.INVALID_IMAGE
)