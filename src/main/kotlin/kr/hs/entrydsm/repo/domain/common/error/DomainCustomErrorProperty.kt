package kr.hs.entrydsm.repo.domain.common.error

interface DomainCustomErrorProperty {

    fun status(): Int

    fun message(): String

    fun code(): String
}