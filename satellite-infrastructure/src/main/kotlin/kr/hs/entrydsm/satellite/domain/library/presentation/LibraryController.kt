package kr.hs.entrydsm.satellite.domain.library.presentation

import java.util.UUID
import javax.validation.constraints.NotNull
import kr.hs.entrydsm.satellite.domain.library.persistence.AccessRight
import kr.hs.entrydsm.satellite.domain.library.presentation.dto.ManagerQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.presentation.dto.StudentQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.usecase.CreateLibraryFileUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.ManagerQueryLibraryUseCase
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

@RequestMapping("/document/library")
@RestController
class LibraryController(
    private val createLibraryFileUseCase: CreateLibraryFileUseCase,
    private val managerQueryLibraryUseCase: ManagerQueryLibraryUseCase,
    private val studentQueryLibraryUseCase: StudentQueryLibraryUseCase,
    private val updateLibraryDocumentAccessRightUseCase: UpdateLibraryDocumentAccessRightUseCase
) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    fun createLibraryFile(
        @RequestParam(name = "grade") @NotNull grade: Int?,
        @RequestParam(name = "secret") @NotNull secret: String?
    ) {
        createLibraryFileUseCase.execute(grade!!, secret!!)
    }

    @GetMapping("/teachers")
    fun managerQueryLibrary(): ManagerQueryLibraryResponse {
        return managerQueryLibraryUseCase.execute()
    }

    @GetMapping("/students")
    fun studentQueryLibrary(): StudentQueryLibraryResponse {
        return studentQueryLibraryUseCase.execute()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{library-document-id}/access-right")
    fun updateLibraryDocumentAccessRight(
        @PathVariable("library-document-id") @NotNull libraryDocumentId: UUID?,
        @RequestParam(name = "access_right") @NotNull accessRight: AccessRight?
    ) {
        return updateLibraryDocumentAccessRightUseCase.execute(
            libraryDocumentId = libraryDocumentId!!,
            accessRight = accessRight!!
        )
    }
}