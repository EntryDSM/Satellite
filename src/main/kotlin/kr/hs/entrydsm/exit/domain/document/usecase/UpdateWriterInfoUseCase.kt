package kr.hs.entrydsm.exit.domain.document.usecase

import DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.common.annotation.UseCase
import kr.hs.entrydsm.exit.domain.common.security.SecurityUtil
import kr.hs.entrydsm.exit.domain.document.exception.MajorNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateWriterInfoRequest
import kr.hs.entrydsm.exit.domain.major.persistence.repository.MajorRepository
import org.springframework.data.repository.findByIdOrNull

@UseCase
class UpdateWriterInfoUseCase(
    private val documentRepository: DocumentRepository,
    private val majorRepository: MajorRepository
) {

    fun execute(request: UpdateWriterInfoRequest) {

        val student = SecurityUtil.getCurrentStudent()

        val document = documentRepository.findByIdAndWriterStudentId(request.documentId, student.id)?: throw DocumentNotFoundException

        documentRepository.save(
            documentWithUpdatedWriterInfo(document, request)
        )
    }

    private fun documentWithUpdatedWriterInfo(
        document: Document,
        request: UpdateWriterInfoRequest,
    ): Document {

        val major = majorRepository.findByIdOrNull(request.majorId) ?: throw MajorNotFoundException

        return request.run {
            document.updateWriterInfo(
                document.writer.updateVariableInfo(
                    profileImagePath = profileImagePath,
                    grade = grade,
                    classNum = classNum,
                    number = number,
                    email = email,
                    major = major
                )
            )
        }
    }

}