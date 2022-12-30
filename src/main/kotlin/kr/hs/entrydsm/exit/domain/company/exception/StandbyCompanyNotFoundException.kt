package kr.hs.entrydsm.exit.domain.company.exception

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode
import kr.hs.entrydsm.exit.domain.common.error.DomainCustomException

object StandbyCompanyNotFoundException : DomainCustomException(
    DomainErrorCode.STANDBY_COMPANY_NOT_FOUND
)