package kr.hs.entrydsm.satellite.domain.library.dto

data class LibraryDocumentResponse(
    val year: Int,
    val grade: Int,
    val generation: Int,
    val documentUrl: String
)