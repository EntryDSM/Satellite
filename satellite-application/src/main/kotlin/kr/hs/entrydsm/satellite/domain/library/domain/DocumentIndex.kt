package kr.hs.entrydsm.satellite.domain.library.domain

data class DocumentIndex(
    val name: String,
    val major: String,
    val studentNumber: Int,
    val page: Int
)