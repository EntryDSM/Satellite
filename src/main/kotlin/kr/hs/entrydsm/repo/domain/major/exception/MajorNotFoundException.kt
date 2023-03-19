package kr.hs.entrydsm.repo.domain.major.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object MajorNotFoundException: DomainCustomException(
    DomainErrorCode.MAJOR_NOT_FOUND
)