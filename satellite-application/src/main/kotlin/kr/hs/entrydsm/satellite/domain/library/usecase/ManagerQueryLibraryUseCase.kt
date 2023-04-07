package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentResponse
import kr.hs.entrydsm.satellite.domain.library.dto.ManagerQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort

@ReadOnlyUseCase
class ManagerQueryLibraryUseCase(
    private val libraryDocumentPort: LibraryDocumentPort,
    private val filePort: FilePort
) {
    fun execute(year: Int?): ManagerQueryLibraryResponse {
        val libraryDocuments = year?.let {
            libraryDocumentPort.queryByYear(year)
        } ?: libraryDocumentPort.queryAll()

        return ManagerQueryLibraryResponse(
            libraryDocuments.map {
                LibraryDocumentResponse(
                    id = it.id,
                    accessRight = it.accessRight,
                    year = it.year,
                    grade = it.grade,
                    generation = it.generation,
                    documentUrl =  filePort.getPdfFileUrl(it.filePath)
                )
            }
        )
    }
}