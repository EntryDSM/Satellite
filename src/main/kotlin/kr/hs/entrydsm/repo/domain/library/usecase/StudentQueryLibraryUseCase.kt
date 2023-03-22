package kr.hs.entrydsm.repo.domain.library.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.library.persistence.AccessRight
import kr.hs.entrydsm.repo.domain.library.persistence.repository.LibraryDocumentRepository
import kr.hs.entrydsm.repo.domain.library.presentation.dto.StudentQueryLibraryResponse

@ReadOnlyUseCase
class StudentQueryLibraryUseCase(
    private val libraryDocumentRepository: LibraryDocumentRepository
) {
    fun execute(): StudentQueryLibraryResponse {
        val libraryDocuments = libraryDocumentRepository.findByAccessRightNot(AccessRight.PRIVATE)
        return StudentQueryLibraryResponse(
            libraryDocuments.map {
                StudentQueryLibraryResponse.LibraryDocumentElement(
                    year = it.year,
                    grade = it.grade,
                    generation = it.generation,
                    documentUrl = it.fileUrl
                )
            }
        )
    }
}