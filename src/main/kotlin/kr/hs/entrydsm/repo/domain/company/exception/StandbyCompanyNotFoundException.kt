package kr.hs.entrydsm.repo.domain.company.exception

import kr.hs.entrydsm.repo.domain.common.DomainErrorCode
import kr.hs.entrydsm.repo.domain.common.error.DomainCustomException

object StandbyCompanyNotFoundException : DomainCustomException(
    DomainErrorCode.STANDBY_COMPANY_NOT_FOUND
)