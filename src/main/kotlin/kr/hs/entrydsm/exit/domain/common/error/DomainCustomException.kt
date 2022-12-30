package kr.hs.entrydsm.exit.domain.common.error

import kr.hs.entrydsm.exit.domain.common.DomainErrorCode

abstract class DomainCustomException(
    val errorProperty: DomainErrorCode
) : RuntimeException()
