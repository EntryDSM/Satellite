package kr.hs.entrydsm.exit.domain.document.presentation

import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.*
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.CreateDocumentResponse
import kr.hs.entrydsm.exit.domain.document.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/document")
@RestController
class DocumentController(
    private val createDocumentUseCase: CreateDocumentUseCase,
    private val updateWriterInfoUseCase: UpdateWriterInfoUseCase,
    private val updateIntroduceUseCase: UpdateIntroduceUseCase,
    private val updateSkillSetUseCase: UpdateSkillSetUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val updateAwardUseCase: UpdateAwardUseCase,
    private val updateCertificateUseCase: UpdateCertificateUseCase
) {
 
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createDocument(@RequestBody @Valid request: CreateDocumentRequest): CreateDocumentResponse {
        return createDocumentUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/writer-info")
    fun updateWriterInfo(@RequestBody @Valid request: UpdateWriterInfoRequest) {
        updateWriterInfoUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/introduce")
    fun updateIntroduce(@RequestBody @Valid request: UpdateIntroduceRequest) {
        updateIntroduceUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/skill-set")
    fun updateSkillSet(@RequestBody @Valid request: UpdateSkillSetRequest) {
        updateSkillSetUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/project")
    fun updateProject(@RequestBody @Valid request: UpdateProjectRequest) {
        updateProjectUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/award")
    fun updateAward(@RequestBody @Valid request: UpdateAwardRequest) {
        updateAwardUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/certificate")
    fun updateCertificate(@RequestBody @Valid request: UpdateCertificateRequest) {
        updateCertificateUseCase.execute(request)
    }

}
