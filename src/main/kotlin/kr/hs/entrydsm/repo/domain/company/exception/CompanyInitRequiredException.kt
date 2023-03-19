package kr.hs.entrydsm.repo.domain.company.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object CompanyInitRequiredException : DomainCustomException(
    DomainErrorCode.COMPANY_INIT_REQUIRED
)