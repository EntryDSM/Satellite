package kr.hs.entrydsm.repo.domain.document.presentation

import java.util.UUID
import javax.validation.Valid
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.CreateDocumentRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.QueryDocumentRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateAwardRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateCertificateRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateIntroduceRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateProjectRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateSkillSetRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateWriterInfoRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.response.CreateDocumentResponse
import kr.hs.entrydsm.repo.domain.document.presentation.dto.response.DocumentInfoResponse
import kr.hs.entrydsm.repo.domain.document.presentation.dto.response.DocumentListResponse
import kr.hs.entrydsm.repo.domain.document.presentation.dto.response.QueryDocumentPagingInfoResponse
import kr.hs.entrydsm.repo.domain.document.usecase.CancelShareDocumentUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.CancelSubmitMyDocumentUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.CreateDocumentUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.QueryDocumentInfoUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.QueryDocumentPagingInfoUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.QueryMyDocumentInfoUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.QuerySharedDocumentUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.QueryStudentDocumentInfoUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.ShareDocumentUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.SubmitMyDocumentUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.UpdateAwardUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.UpdateCertificateUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.UpdateIntroduceUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.UpdateProjectUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.UpdateSkillSetUseCase
import kr.hs.entrydsm.repo.domain.document.usecase.UpdateWriterInfoUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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
    private val queryStudentDocumentInfoUseCase: QueryStudentDocumentInfoUseCase,
    private val querySharedDocumentUseCase: QuerySharedDocumentUseCase,
    private val queryDocumentPagingInfoUseCase: QueryDocumentPagingInfoUseCase,

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

    @GetMapping("/student/{student-id}")
    fun queryStudentDocumentInfo(@PathVariable("student-id") studentId: UUID): DocumentInfoResponse {
        return queryStudentDocumentInfoUseCase.execute(studentId)
    }

    @GetMapping("/{document-id}")
    fun queryDocumentInfo(@PathVariable("document-id") documentId: UUID): DocumentInfoResponse {
        return queryDocumentInfoUseCase.execute(documentId)
    }

    @GetMapping("/shared")
    fun querySharedDocument(@ModelAttribute @Valid request: QueryDocumentRequest): DocumentListResponse {
        return querySharedDocumentUseCase.execute(request)
    }

    @GetMapping("/{document-id}/paging")
    fun queryDocumentPagingInfoUseCase(
        @PathVariable("document-id") documentId: UUID,
        @RequestParam(defaultValue = "SHARED") status: Status
    ): QueryDocumentPagingInfoResponse {
        return queryDocumentPagingInfoUseCase.execute(documentId, status)
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