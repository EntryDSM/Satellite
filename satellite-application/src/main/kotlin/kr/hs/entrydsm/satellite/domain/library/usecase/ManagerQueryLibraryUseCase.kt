package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.library.dto.ManagerQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort

@ReadOnlyUseCase
class ManagerQueryLibraryUseCase(
    private val libraryDocumentPort: LibraryDocumentPort
) {
    fun execute(year: Int?): ManagerQueryLibraryResponse {
        val libraryDocuments = year?.let {
            libraryDocumentPort.queryByYear(year)
        } ?: libraryDocumentPort.queryAll()

        return ManagerQueryLibraryResponse(
            libraryDocuments.map {
                ManagerQueryLibraryResponse.LibraryDocumentElement(
                    id = it.id,
                    accessRight = it.accessRight,
                    year = it.year,
                    grade = it.grade,
                    generation = it.generation,
                    url = it.fileUrl
                )
            }
        )
    }
}