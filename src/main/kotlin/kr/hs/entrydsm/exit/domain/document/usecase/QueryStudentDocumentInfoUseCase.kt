package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.exit.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.DocumentInfoResponse
import kr.hs.entrydsm.exit.domain.feedback.persistence.repository.FeedbackRepository
import kr.hs.entrydsm.exit.domain.student.exception.StudentNotFoundException
import kr.hs.entrydsm.exit.domain.student.persistence.repository.StudentRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@ReadOnlyUseCase
class QueryStudentDocumentInfoUseCase(
    private val studentRepository: StudentRepository,
    private val documentRepository: DocumentRepository,
    private val feedbackRepository: FeedbackRepository
) {

    fun execute(studentId: UUID): DocumentInfoResponse {

        val student = studentRepository.findByIdOrNull(studentId) ?: throw StudentNotFoundException
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        val feedbackList = feedbackRepository.findByDocumentId(document.id)
        val feedbackMap = feedbackList.associate { it.elementId to it.comment }

        return DocumentInfoResponse(document, feedbackMap)
    }
}