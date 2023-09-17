package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentIndexResponse
import kr.hs.entrydsm.satellite.domain.library.exception.LibraryDocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import java.util.*

@ReadOnlyUseCase
class QueryLibraryDocumentIndexUseCase(
    private val libraryDocumentPort: LibraryDocumentPort
) {
    suspend fun execute(libraryDocumentId: UUID): LibraryDocumentIndexResponse {
        val libraryDocument = libraryDocumentPort.queryById(libraryDocumentId) ?: throw LibraryDocumentNotFoundException
        return LibraryDocumentIndexResponse(
            libraryDocument.index
                .map { it.copy(major = it.major.split(" ")[0]) }
        )
    }
}