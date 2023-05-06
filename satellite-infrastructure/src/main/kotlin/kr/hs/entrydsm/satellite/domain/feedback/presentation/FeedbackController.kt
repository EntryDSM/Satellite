package kr.hs.entrydsm.satellite.domain.feedback.presentation

import kr.hs.entrydsm.satellite.domain.feedback.dto.FeedbackListResponse
import kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request.ApplyFeedbackRequest
import kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request.CreateFeedbackRequest
import kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request.DeleteFeedbackRequest
import kr.hs.entrydsm.satellite.domain.feedback.presentation.dto.request.UpdateFeedbackWebRequest
import kr.hs.entrydsm.satellite.domain.feedback.usecase.ApplyFeedbackUseCase
import kr.hs.entrydsm.satellite.domain.feedback.usecase.CreateFeedbackUseCase
import kr.hs.entrydsm.satellite.domain.feedback.usecase.DeleteFeedbackUseCase
import kr.hs.entrydsm.satellite.domain.feedback.usecase.QueryMyDocumentFeedbackUseCase
import kr.hs.entrydsm.satellite.domain.feedback.usecase.UpdateFeedbackUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RequestMapping("/feedback")
@RestController
class FeedbackController(
    private val createFeedbackUseCase: CreateFeedbackUseCase,
    private val deleteFeedbackUseCase: DeleteFeedbackUseCase,
    private val updateFeedbackUseCase: UpdateFeedbackUseCase,
    private val applyFeedbackUseCase: ApplyFeedbackUseCase,
    private val queryMyDocumentFeedbackUseCase: QueryMyDocumentFeedbackUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    suspend fun createFeedback(@RequestBody @Valid request: CreateFeedbackRequest) {
        createFeedbackUseCase.execute(
            documentId = request.documentId!!,
            elementId = request.elementId!!,
            comment = request.comment!!
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping
    suspend fun updateFeedback(@RequestBody @Valid request: UpdateFeedbackWebRequest) {
        updateFeedbackUseCase.execute(
            documentId = request.documentId!!,
            elementId = request.elementId!!,
            comment = request.comment!!
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    suspend fun deleteFeedback(@RequestBody @Valid request: DeleteFeedbackRequest) {
        deleteFeedbackUseCase.execute(
            documentId = request.documentId!!,
            elementId = request.elementId!!
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/apply")
    suspend fun applyFeedback(@RequestBody @Valid request: ApplyFeedbackRequest) {
        applyFeedbackUseCase.execute(
            documentId = request.documentId!!,
            elementId = request.elementId!!,
        )
    }

    @GetMapping("/my")
    suspend fun queryMyDocumentFeedback(): FeedbackListResponse {
        return queryMyDocumentFeedbackUseCase.execute()
    }
}