package kr.hs.entrydsm.repo.domain.file.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object InvalidImageException : DomainCustomException(
    DomainErrorCode.INVALID_IMAGE
)