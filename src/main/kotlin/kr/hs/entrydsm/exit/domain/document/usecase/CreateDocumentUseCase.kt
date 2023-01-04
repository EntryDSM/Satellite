package kr.hs.entrydsm.exit.domain.document.usecase

import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.security.SecurityUtil
import kr.hs.entrydsm.exit.domain.document.exception.DocumentAlreadyExistException
import kr.hs.entrydsm.exit.domain.document.exception.MajorNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.element.WriterInfoElement
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.CreateDocumentRequest
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.CreateDocumentResponse
import kr.hs.entrydsm.exit.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import org.springframework.data.repository.findByIdOrNull

@UseCase
class CreateDocumentUseCase(
    private val documentRepository: DocumentRepository,
    private val majorRepository: MajorRepository
) {

    fun execute(request: CreateDocumentRequest): CreateDocumentResponse {

        val student = SecurityUtil.getCurrentStudent()

        if(writerIsExist(student)){
            throw DocumentAlreadyExistException
        }

        val major = majorRepository.findByIdOrNull(request.majorId) ?: throw MajorNotFoundException

        val document = documentRepository.save(
            Document(
                writer = WriterInfoElement(student, major),
                status = Status.CREATED
            )
        )

        return CreateDocumentResponse(document.id)
    }

    private fun writerIsExist(student: Student): Boolean {
       return documentRepository.findByWriterStudentId(student.id) != null
    }

}