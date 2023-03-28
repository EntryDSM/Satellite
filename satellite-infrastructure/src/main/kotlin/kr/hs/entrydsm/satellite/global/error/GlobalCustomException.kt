package kr.hs.entrydsm.satellite.global.error

abstract class GlobalCustomException(
    override val errorProperty: CustomErrorProperty
) : CustomException(errorProperty)