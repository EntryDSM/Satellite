package kr.hs.entrydsm.exit.domain.document.presentation

import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.*
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.CreateDocumentResponse
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.DocumentInfoResponse
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.DocumentListResponse
import kr.hs.entrydsm.exit.domain.document.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RequestMapping("/document")
@RestController
class DocumentController(
    private val createDocumentUseCase: CreateDocumentUseCase,

    private val updateWriterInfoUseCase: UpdateWriterInfoUseCase,
    private val updateIntroduceUseCase: UpdateIntroduceUseCase,
    private val updateSkillSetUseCase: UpdateSkillSetUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val updateAwardUseCase: UpdateAwardUseCase,
    private val updateCertificateUseCase: UpdateCertificateUseCase,

    private val queryDocumentInfoUseCase: QueryDocumentInfoUseCase,
    private val queryMyDocumentInfoUseCase: QueryMyDocumentInfoUseCase,
    private val querySharedDocumentUseCase: QuerySharedDocumentUseCase,

    private val submitMyDocumentUseCase: SubmitMyDocumentUseCase,
    private val cancelSubmitMyDocumentUseCase: CancelSubmitMyDocumentUseCase,
    private val shareDocumentUseCase: ShareDocumentUseCase,
    private val cancelShareDocumentUseCase: CancelShareDocumentUseCase
) {
 
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createDocument(@RequestBody @Valid request: CreateDocumentRequest): CreateDocumentResponse {
        return createDocumentUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/writer-info")
    fun updateWriterInfo(@RequestBody @Valid request: UpdateWriterInfoRequest) {
        updateWriterInfoUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/introduce")
    fun updateIntroduce(@RequestBody @Valid request: UpdateIntroduceRequest) {
        updateIntroduceUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/skill-set")
    fun updateSkillSet(@RequestBody @Valid request: UpdateSkillSetRequest) {
        updateSkillSetUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/project")
    fun updateProject(@RequestBody @Valid request: UpdateProjectRequest) {
        updateProjectUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/award")
    fun updateAward(@RequestBody @Valid request: UpdateAwardRequest) {
        updateAwardUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/certificate")
    fun updateCertificate(@RequestBody @Valid request: UpdateCertificateRequest) {
        updateCertificateUseCase.execute(request)
    }

    @GetMapping("/my")
    fun queryMyDocumentInfo(): DocumentInfoResponse {
        return queryMyDocumentInfoUseCase.execute()
    }

    @GetMapping("/shared")
    fun querySharedDocument(@ModelAttribute @Valid request: QueryDocumentRequest): DocumentListResponse {
        return querySharedDocumentUseCase.execute(request)
    }

    @GetMapping("/{document-id}")
    fun queryDocumentInfo(@PathVariable("document-id") documentId: UUID): DocumentInfoResponse {
        return queryDocumentInfoUseCase.execute(documentId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/submit")
    fun submitDocument() {
        submitMyDocumentUseCase.execute()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/submit/cancel")
    fun cancelSubmitDocument() {
        cancelSubmitMyDocumentUseCase.execute()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/share/{document-id}")
    fun shareDocument(@PathVariable("document-id") documentId: UUID) {
        shareDocumentUseCase.execute(documentId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/share/cancel/{document-id}")
    fun cancelShareDocument(@PathVariable("document-id") documentId: UUID) {
        cancelShareDocumentUseCase.execute(documentId)
    }

}
