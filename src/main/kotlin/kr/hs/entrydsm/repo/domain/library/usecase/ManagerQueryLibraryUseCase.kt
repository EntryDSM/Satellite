package kr.hs.entrydsm.repo.domain.library.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.library.persistence.repository.LibraryDocumentRepository
import kr.hs.entrydsm.repo.domain.library.presentation.dto.ManagerQueryLibraryResponse

@ReadOnlyUseCase
class ManagerQueryLibraryUseCase(
    private val libraryDocumentRepository: LibraryDocumentRepository
) {
    fun execute(): ManagerQueryLibraryResponse {
        val libraryDocuments = libraryDocumentRepository.findAll()
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