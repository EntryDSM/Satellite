package kr.hs.entrydsm.exit.global.error.custom

abstract class GlobalCustomException(
    val errorProperty: kr.hs.entrydsm.exit.global.error.custom.CustomErrorProperty
) : RuntimeException()