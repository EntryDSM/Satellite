package kr.hs.entrydsm.exit.domain.file.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object InvalidImageException : DomainCustomException(
    DomainErrorCode.INVALID_IMAGE
)