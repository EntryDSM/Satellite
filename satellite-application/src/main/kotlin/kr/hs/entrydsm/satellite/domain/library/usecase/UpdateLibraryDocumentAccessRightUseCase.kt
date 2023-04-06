package kr.hs.entrydsm.satellite.domain.library.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.exception.LibraryDocumentNotFoundException
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import java.util.*

@ReadOnlyUseCase
class UpdateLibraryDocumentAccessRightUseCase(
    private val libraryDocumentPort: LibraryDocumentPort
) {
    fun execute(libraryDocumentId: UUID, accessRight: AccessRight) {
        val libraryDocument =
            libraryDocumentPort.queryById(libraryDocumentId) ?: throw LibraryDocumentNotFoundException

        libraryDocumentPort.save(
            libraryDocument.copy(accessRight = accessRight)
        )
    }
}