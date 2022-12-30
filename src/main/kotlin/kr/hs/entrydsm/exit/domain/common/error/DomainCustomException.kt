package kr.hs.entrydsm.exit.domain.common.error

import kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty
import kr.hs.entrydsm.exit.global.error.custom.CustomException

abstract class DomainCustomException(
    override val errorProperty: CustomErrorProperty
) : CustomException(errorProperty)
