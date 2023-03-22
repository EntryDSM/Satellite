package kr.hs.entrydsm.repo.domain.library.presentation.dto

import java.util.UUID
import kr.hs.entrydsm.repo.domain.library.persistence.AccessRight

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