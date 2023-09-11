package kr.hs.entrydsm.satellite.domain.document.presentation

import javax.validation.Valid
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentInfoResponse
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentListResponse
import kr.hs.entrydsm.satellite.domain.document.dto.DocumentResponse
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.QueryDocumentWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateActivityWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateAwardWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateCertificateWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateIntroduceWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateProfileImageWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateProjectWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateSkillSetWebRequest
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.UpdateWriterInfoWebRequest
import kr.hs.entrydsm.satellite.domain.document.usecase.CancelShareDocumentUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.CancelSubmitMyDocumentUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.QueryDocumentInfoUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.QueryMyDocumentInfoUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.QueryMyDocumentUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.QuerySharedDocumentUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.QueryStudentDocumentInfoUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.ShareDocumentUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.SubmitMyDocumentUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateActivityUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateAwardUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateCertificateUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateIntroduceUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateProfileImageUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateProjectUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateSkillSetUseCase
import kr.hs.entrydsm.satellite.domain.document.usecase.UpdateWriterInfoUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping("/document")
@RestController
class DocumentController(
    private val updateWriterInfoUseCase: UpdateWriterInfoUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    private val updateIntroduceUseCase: UpdateIntroduceUseCase,
    private val updateSkillSetUseCase: UpdateSkillSetUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val updateAwardUseCase: UpdateAwardUseCase,
    private val updateCertificateUseCase: UpdateCertificateUseCase,
    private val updateActivityUseCase: UpdateActivityUseCase,

    private val queryDocumentInfoUseCase: QueryDocumentInfoUseCase,
    private val queryMyDocumentInfoUseCase: QueryMyDocumentInfoUseCase,
    private val queryMyDocumentUseCase: QueryMyDocumentUseCase,
    private val queryStudentDocumentInfoUseCase: QueryStudentDocumentInfoUseCase,
    private val querySharedDocumentUseCase: QuerySharedDocumentUseCase,

    private val submitMyDocumentUseCase: SubmitMyDocumentUseCase,
    private val cancelSubmitMyDocumentUseCase: CancelSubmitMyDocumentUseCase,
    private val shareDocumentUseCase: ShareDocumentUseCase,
    private val cancelShareDocumentUseCase: CancelShareDocumentUseCase
) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/writer-info")
    suspend fun updateWriterInfo(@RequestBody @Valid request: UpdateWriterInfoWebRequest) {
        updateWriterInfoUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/profile-image")
    suspend fun updateProfileImage(@RequestBody @Valid request: UpdateProfileImageWebRequest) {
        updateProfileImageUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/introduce")
    suspend fun updateIntroduce(@RequestBody @Valid request: UpdateIntroduceWebRequest) {
        updateIntroduceUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/skill-set")
    suspend fun updateSkillSet(@RequestBody @Valid request: UpdateSkillSetWebRequest) {
        updateSkillSetUseCase.execute(request.skillList)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/project")
    suspend fun updateProject(@RequestBody @Valid request: UpdateProjectWebRequest) {
        updateProjectUseCase.execute(request.projectList)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/award")
    suspend fun updateAward(@RequestBody @Valid request: UpdateAwardWebRequest) {
        updateAwardUseCase.execute(request.awardList)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/certificate")
    suspend fun updateCertificate(@RequestBody @Valid request: UpdateCertificateWebRequest) {
        updateCertificateUseCase.execute(request.certificateList)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/activity")
    suspend fun updateActivity(@RequestBody @Valid request: UpdateActivityWebRequest) {
        updateActivityUseCase.execute(request.activityList)
    }

    @GetMapping("/my/detail")
    suspend fun queryMyDocumentInfo(): DocumentInfoResponse {
        return queryMyDocumentInfoUseCase.execute()
    }

    @GetMapping("/my")
    suspend fun queryMyDocument(): DocumentResponse {
        return queryMyDocumentUseCase.execute()
    }

    @GetMapping("/student/{student-id}")
    suspend fun queryStudentDocumentInfo(@PathVariable("student-id") studentId: UUID): DocumentInfoResponse {
        return queryStudentDocumentInfoUseCase.execute(studentId)
    }

    @GetMapping("/{document-id}")
    suspend fun queryDocumentInfo(@PathVariable("document-id") documentId: UUID): DocumentInfoResponse {
        return queryDocumentInfoUseCase.execute(documentId)
    }

    @GetMapping("/shared")
    suspend fun querySharedDocument(@ModelAttribute @Valid request: QueryDocumentWebRequest): DocumentListResponse {
        return request.run {
            querySharedDocumentUseCase.execute(
                name = name,
                grade = grade,
                classNum = classNum,
                majorId = majorId
            )
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/submit")
    suspend fun submitDocument() {
        submitMyDocumentUseCase.execute()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/submit/cancel")
    suspend fun cancelSubmitDocument() {
        cancelSubmitMyDocumentUseCase.execute()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/share/{document-id}")
    suspend fun shareDocument(@PathVariable("document-id") documentId: UUID) {
        shareDocumentUseCase.execute(documentId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/share/cancel/{document-id}")
    suspend fun cancelShareDocument(@PathVariable("document-id") documentId: UUID) {
        cancelShareDocumentUseCase.execute(documentId)
    }
}