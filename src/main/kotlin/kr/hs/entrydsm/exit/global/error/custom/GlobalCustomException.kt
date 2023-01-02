package kr.hs.entrydsm.exit.global.error.custom

abstract class GlobalCustomException(
    override val errorProperty: CustomErrorProperty
) : CustomException(errorProperty)