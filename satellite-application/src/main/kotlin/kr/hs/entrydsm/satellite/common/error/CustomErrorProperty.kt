package kr.hs.entrydsm.satellite.common.error

interface CustomErrorProperty {
    fun status(): Int
    fun message(): String
    fun code(): String
}