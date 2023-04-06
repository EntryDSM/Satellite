package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentResponse
import kr.hs.entrydsm.satellite.domain.library.exception.LibraryDocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import java.util.*

@ReadOnlyUseCase
class QueryLibraryDocumentUseCase(
    private val libraryDocumentPort: LibraryDocumentPort,
    private val filePort: FilePort
) {
    fun execute(libraryDocumentId: UUID): LibraryDocumentResponse {
        val libraryDocument = libraryDocumentPort.queryById(libraryDocumentId)?.let {
            if (it.accessRight != AccessRight.PUBLIC) null else it
        } ?: throw LibraryDocumentNotFoundException

        return libraryDocument.run {
            LibraryDocumentResponse(
                year = year,
                grade = grade,
                generation = generation,
                documentUrl = filePort.getPdfFileUrl(filePath)
            )
        }
    }
}