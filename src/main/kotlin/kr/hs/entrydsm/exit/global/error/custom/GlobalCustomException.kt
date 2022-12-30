package kr.hs.entrydsm.exit.global.error.custom

abstract class GlobalCustomException(
    val errorProperty: CustomErrorProperty
) : RuntimeException()