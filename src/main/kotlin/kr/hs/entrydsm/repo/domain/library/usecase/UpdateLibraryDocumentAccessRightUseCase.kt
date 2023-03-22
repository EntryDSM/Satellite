package kr.hs.entrydsm.repo.domain.library.usecase

import java.util.UUID
import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.library.exception.LibraryDocumentNotFoundException
import kr.hs.entrydsm.repo.domain.library.persistence.AccessRight
import kr.hs.entrydsm.repo.domain.library.persistence.repository.LibraryDocumentRepository
import org.springframework.data.repository.findByIdOrNull

@ReadOnlyUseCase
class UpdateLibraryDocumentAccessRightUseCase(
    private val libraryDocumentRepository: LibraryDocumentRepository
) {
    fun execute(libraryDocumentId: UUID, accessRight: AccessRight) {
        val libraryDocument =
            libraryDocumentRepository.findByIdOrNull(libraryDocumentId) ?: throw LibraryDocumentNotFoundException

        libraryDocumentRepository.save(
            libraryDocument.updateLibraryDocumentRight(accessRight)
        )
    }
}