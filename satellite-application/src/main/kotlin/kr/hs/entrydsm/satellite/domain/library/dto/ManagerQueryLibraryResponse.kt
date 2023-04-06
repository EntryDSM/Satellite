package kr.hs.entrydsm.satellite.domain.library.dto

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import java.util.*

data class ManagerQueryLibraryResponse(
    val libraryDocumentList: List<LibraryDocumentElement>
) {
    data class LibraryDocumentElement(
        val id: UUID,
        val accessRight: AccessRight,
        val year: Int,
        val grade: Int,
        val generation: Int,
        val url: String
    )
}