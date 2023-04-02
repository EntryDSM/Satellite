package kr.hs.entrydsm.satellite.global.error

import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty
import kr.hs.entrydsm.satellite.common.error.CustomException

abstract class GlobalCustomException(
    override val errorProperty: CustomErrorProperty
) : CustomException(errorProperty)