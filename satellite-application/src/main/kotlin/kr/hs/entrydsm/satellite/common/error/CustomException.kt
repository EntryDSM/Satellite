package kr.hs.entrydsm.satellite.common.error

abstract class CustomException(
    open val errorProperty: CustomErrorProperty
) : RuntimeException()