package kr.hs.entrydsm.satellite.common.error

abstract class DomainCustomException(
    override val errorProperty: CustomErrorProperty
) : CustomException(errorProperty)