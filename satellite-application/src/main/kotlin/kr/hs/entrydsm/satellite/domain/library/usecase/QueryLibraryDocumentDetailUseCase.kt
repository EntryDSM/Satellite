package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentDetailResponse
import kr.hs.entrydsm.satellite.domain.library.exception.LibraryDocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import java.util.*

@ReadOnlyUseCase
class QueryLibraryDocumentDetailUseCase(
    private val libraryDocumentPort: LibraryDocumentPort,
    private val filePort: FilePort
) {
    suspend fun execute(libraryDocumentId: UUID): LibraryDocumentDetailResponse {
        val libraryDocument = libraryDocumentPort.queryById(libraryDocumentId)?.let {
            if (it.accessRight != AccessRight.PUBLIC) null else it
        } ?: throw LibraryDocumentNotFoundException

        return libraryDocument.run {
            LibraryDocumentDetailResponse(
                id = id,
                year = year,
                grade = grade,
                generation = generation,
                documentUrl = filePort.getPdfFileUrl(filePath),
                index = libraryDocument.index
            )
        }
    }
}