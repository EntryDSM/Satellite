package com.example.exit.domain.company.exception

import com.example.exit.domain.common.DomainErrorCode
import com.example.exit.domain.common.error.DomainCustomException

object StandbyCompanyNotFoundException : DomainCustomException(
    DomainErrorCode.STANDBY_COMPANY_NOT_FOUND
)