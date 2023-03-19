package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentAlreadyExistException
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.element.WriterInfoElement
import kr.hs.entrydsm.repo.domain.document.persistence.enums.Status
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.CreateDocumentRequest
import kr.hs.entrydsm.repo.domain.document.presentation.dto.response.CreateDocumentResponse
import kr.hs.entrydsm.repo.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.repo.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.repo.domain.school.facade.SchoolYearFacade
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.global.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull

@UseCase
class CreateDocumentUseCase(
    private val documentRepository: DocumentRepository,
    private val majorRepository: MajorRepository,
    private val schoolYearFacade: SchoolYearFacade
) {

    fun execute(request: CreateDocumentRequest): CreateDocumentResponse {

        val student = SecurityUtil.getCurrentStudent()

        if (documentIsExist(student)){
            throw DocumentAlreadyExistException
        }

        val major = majorRepository.findByIdOrNull(request.majorId) ?: throw MajorNotFoundException

        val document = documentRepository.save(
            Document(
                writer = WriterInfoElement(student, major),
                year = schoolYearFacade.getSchoolYear(),
                status = Status.CREATED
            )
        )

        return CreateDocumentResponse(document.id)
    }

    private fun documentIsExist(student: Student): Boolean {
       return documentRepository.findByWriterStudentId(student.id) != null
    }

}