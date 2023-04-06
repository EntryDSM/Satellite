package kr.hs.entrydsm.satellite.domain.library.presentation

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentResponse
import kr.hs.entrydsm.satellite.domain.library.dto.ManagerQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.dto.StudentQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.usecase.CreateLibraryFileUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.ManagerQueryLibraryUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.QueryLibraryDocumentUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.StudentQueryLibraryUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.UpdateLibraryDocumentAccessRightUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping("/library")
@RestController
class LibraryController(
    private val createLibraryFileUseCase: CreateLibraryFileUseCase,
    private val managerQueryLibraryUseCase: ManagerQueryLibraryUseCase,
    private val studentQueryLibraryUseCase: StudentQueryLibraryUseCase,
    private val queryLibraryDocumentUseCase: QueryLibraryDocumentUseCase,
    private val updateLibraryDocumentAccessRightUseCase: UpdateLibraryDocumentAccessRightUseCase
) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    fun createLibraryFile(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "secret") secret: String
    ) {
        createLibraryFileUseCase.execute(grade, secret)
    }

    @GetMapping("/teacher")
    fun managerQueryLibrary(
        @RequestParam(name = "year") year: Int?,
    ): ManagerQueryLibraryResponse {
        return managerQueryLibraryUseCase.execute(year)
    }

    @GetMapping("/student")
    fun studentQueryLibrary(
        @RequestParam(name = "year") year: Int?,
    ): StudentQueryLibraryResponse {
        return studentQueryLibraryUseCase.execute(year)
    }

    @GetMapping("/public/{library-document-id}")
    fun queryLibraryDocument(
        @PathVariable("library-document-id") libraryDocumentId: UUID,
    ): LibraryDocumentResponse {
        return queryLibraryDocumentUseCase.execute(libraryDocumentId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{library-document-id}/access-right")
    fun updateLibraryDocumentAccessRight(
        @PathVariable("library-document-id") libraryDocumentId: UUID,
        @RequestParam(name = "access_right") accessRight: AccessRight
    ) {
        return updateLibraryDocumentAccessRightUseCase.execute(
            libraryDocumentId = libraryDocumentId,
            accessRight = accessRight
        )
    }
}