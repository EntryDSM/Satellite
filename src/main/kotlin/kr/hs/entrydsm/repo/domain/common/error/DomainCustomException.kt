package kr.hs.entrydsm.repo.domain.common.error

import kr.hs.entrydsm.repo.global.error.custom.CustomErrorProperty
import kr.hs.entrydsm.repo.global.error.custom.CustomException

abstract class DomainCustomException(
    override val errorProperty: CustomErrorProperty
) : CustomException(errorProperty)
