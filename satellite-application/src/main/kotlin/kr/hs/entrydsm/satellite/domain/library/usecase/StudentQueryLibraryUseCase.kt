package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentResponse
import kr.hs.entrydsm.satellite.domain.library.dto.StudentQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort

@ReadOnlyUseCase
class StudentQueryLibraryUseCase(
    private val libraryDocumentPort: LibraryDocumentPort
) {
    fun execute(): StudentQueryLibraryResponse {
        val libraryDocuments = libraryDocumentPort.queryByAccessRightNot(AccessRight.PRIVATE)
        return StudentQueryLibraryResponse(
            libraryDocuments.map {
                LibraryDocumentResponse(
                    year = it.year,
                    grade = it.grade,
                    generation = it.generation,
                    documentUrl = it.fileUrl
                )
            }
        )
    }
}