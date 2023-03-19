package kr.hs.entrydsm.repo.domain.feedback.presentation

import kr.hs.entrydsm.repo.domain.feedback.presentation.dto.request.ApplyFeedbackRequest
import kr.hs.entrydsm.repo.domain.feedback.presentation.dto.request.CreateFeedbackRequest
import kr.hs.entrydsm.repo.domain.feedback.presentation.dto.request.DeleteFeedbackRequest
import kr.hs.entrydsm.repo.domain.feedback.presentation.dto.request.UpdateFeedbackRequest
import kr.hs.entrydsm.repo.domain.feedback.presentation.dto.response.FeedbackListResponse
import kr.hs.entrydsm.repo.domain.feedback.usecase.ApplyFeedbackUseCase
import kr.hs.entrydsm.repo.domain.feedback.usecase.CreateFeedbackUseCase
import kr.hs.entrydsm.repo.domain.feedback.usecase.DeleteFeedbackUseCase
import kr.hs.entrydsm.repo.domain.feedback.usecase.QueryMyDocumentFeedbackUseCase
import kr.hs.entrydsm.repo.domain.feedback.usecase.UpdateFeedbackUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
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
    fun createFeedback(@RequestBody @Valid request: CreateFeedbackRequest) {
        createFeedbackUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping
    fun updateFeedback(@RequestBody @Valid request: UpdateFeedbackRequest) {
        updateFeedbackUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping
    fun deleteFeedback(@RequestBody @Valid request: DeleteFeedbackRequest) {
        deleteFeedbackUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/apply")
    fun applyFeedback(@RequestBody @Valid request: ApplyFeedbackRequest) {
        applyFeedbackUseCase.execute(request)
    }

    @GetMapping("/my")
    fun queryMyDocumentFeedback(): FeedbackListResponse {
        return queryMyDocumentFeedbackUseCase.execute()
    }

}