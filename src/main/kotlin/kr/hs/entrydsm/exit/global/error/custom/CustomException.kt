package kr.hs.entrydsm.exit.global.error.custom

abstract class CustomException(
    open val errorProperty: CustomErrorProperty
) : RuntimeException()
