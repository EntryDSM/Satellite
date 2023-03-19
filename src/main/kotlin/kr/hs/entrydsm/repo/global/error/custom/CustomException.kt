package kr.hs.entrydsm.repo.global.error.custom

abstract class CustomException(
    open val errorProperty: CustomErrorProperty
) : RuntimeException()