package kr.hs.entrydsm.exit.domain.company.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object CompanyInitRequiredException : DomainCustomException(
    DomainErrorCode.COMPANY_INIT_REQUIRED
)