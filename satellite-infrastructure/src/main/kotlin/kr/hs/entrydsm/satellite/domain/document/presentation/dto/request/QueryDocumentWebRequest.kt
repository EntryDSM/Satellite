package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import java.util.*

data class QueryDocumentWebRequest(
    val name: String?,
    val grade: String?,
    val classNum: String?,
    val majorId: UUID?
)