package kr.hs.entrydsm.repo.domain.library.presentation.dto

import kr.hs.entrydsm.repo.domain.library.persistence.AccessRight
import java.util.UUID

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