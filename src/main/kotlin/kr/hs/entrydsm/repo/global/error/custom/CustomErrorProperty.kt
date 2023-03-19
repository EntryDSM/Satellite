package kr.hs.entrydsm.repo.global.error.custom

interface CustomErrorProperty {

    fun status(): Int

    fun message(): String

    fun code(): String
}