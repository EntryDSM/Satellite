package kr.hs.entrydsm.exit.domain.document.usecase

import DocumentNotFoundException
import kr.hs.entrydsm.exit.domain.common.security.SecurityUtil
import kr.hs.entrydsm.exit.domain.document.exception.MajorNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.UpdateWriterInfoRequest
import kr.hs.entrydsm.exit.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.exit.domain.major.presentation.dto.response.MajorElement
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Transactional(isolation = Isolation.REPEATABLE_READ)
@Service
class UpdateWriterInfoUseCase(
    private val documentRepository: DocumentRepository,
    private val majorRepository: MajorRepository
) {
    @Transactional
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
                    major = MajorElement(major)
                )
            )
        }
    }

}