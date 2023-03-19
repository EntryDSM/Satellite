package kr.hs.entrydsm.repo.domain.auth.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object NotVerifiedException : DomainCustomException(
    DomainErrorCode.NOT_VERIFIED
)