package kr.hs.entrydsm.satellite.domain.library.dto

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import java.util.*

data class LibraryDocumentResponse(
    val id: UUID,
    val accessRight: AccessRight? = null,
    val year: Int,
    val grade: Int,
    val generation: Int,
    val documentUrl: String
)