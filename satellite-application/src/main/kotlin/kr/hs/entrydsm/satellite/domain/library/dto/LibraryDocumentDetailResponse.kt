package kr.hs.entrydsm.satellite.domain.library.dto

import kr.hs.entrydsm.satellite.domain.library.domain.DocumentIndexResponse
import java.util.*

data class LibraryDocumentDetailResponse(
    val id: UUID,
    val year: Int,
    val grade: Int,
    val generation: Int,
    val documentUrl: String,
    val index: List<DocumentIndexResponse>
)