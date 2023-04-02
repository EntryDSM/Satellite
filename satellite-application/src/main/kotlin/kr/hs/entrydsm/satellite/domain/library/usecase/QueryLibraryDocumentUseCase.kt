package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentResponse
import kr.hs.entrydsm.satellite.domain.library.exception.LibraryDocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import java.util.*

@ReadOnlyUseCase
class QueryLibraryDocumentUseCase(
    private val libraryDocumentPort: LibraryDocumentPort
) {
    fun execute(libraryDocumentId: UUID): LibraryDocumentResponse {
        val libraryDocument = libraryDocumentPort.queryById(libraryDocumentId) ?: throw LibraryDocumentNotFoundException
        return libraryDocument.run {
            LibraryDocumentResponse(
                year = year,
                grade = grade,
                generation = generation,
                documentUrl = fileUrl
            )
        }
    }
}