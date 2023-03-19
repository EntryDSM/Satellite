package kr.hs.entrydsm.repo.global.error.custom

abstract class GlobalCustomException(
    override val errorProperty: CustomErrorProperty
) : CustomException(errorProperty)