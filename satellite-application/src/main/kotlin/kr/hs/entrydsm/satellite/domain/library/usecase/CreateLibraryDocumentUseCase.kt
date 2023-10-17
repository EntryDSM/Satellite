package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.DocumentIndex
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocumentDomain
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import kr.hs.entrydsm.satellite.domain.library.spi.SchoolYearPort

@UseCase
class CreateLibraryDocumentUseCase(
    private val libraryDocumentPort: LibraryDocumentPort,
    private val schoolYearPort: SchoolYearPort
) {
    suspend fun execute(
        grade: Int,
        documentIndex: List<DocumentIndex>,
        filePath: String
    ) {
        println("CreateLibraryDocumentUseCase.execute")
        val year = schoolYearPort.getSchoolYear().year

        println("CreateLibraryDocumentUseCase.execute22222")
        libraryDocumentPort.save(
            LibraryDocumentDomain(
                index = documentIndex,
                filePath = filePath,
                year = year,
                grade = grade,
                accessRight = AccessRight.PRIVATE,
            )
        )
    }
}