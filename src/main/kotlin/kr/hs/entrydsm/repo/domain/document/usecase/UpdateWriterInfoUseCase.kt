package kr.hs.entrydsm.repo.domain.document.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.document.exception.DocumentNotFoundException
import kr.hs.entrydsm.repo.domain.document.persistence.Document
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.domain.document.presentation.dto.request.UpdateWriterInfoRequest
import kr.hs.entrydsm.repo.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.repo.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.repo.domain.student.persistence.Student
import kr.hs.entrydsm.repo.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.repo.global.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull

@UseCase
class UpdateWriterInfoUseCase(
    private val documentRepository: DocumentRepository,
    private val studentRepository: StudentRepository,
    private val majorRepository: MajorRepository
) {
    fun execute(request: UpdateWriterInfoRequest) {

        val student = SecurityUtil.getCurrentStudent()
        val document = documentRepository.findByWriterStudentId(student.id) ?: throw DocumentNotFoundException

        studentRepository.save(
            studentWithUpdatedInfo(student, request)
        )

        documentRepository.save(
            documentWithUpdatedWriterInfo(document, request)
        )
    }

    private fun studentWithUpdatedInfo(student: Student, request: UpdateWriterInfoRequest) =
        student.updateVariableInfo(
            grade = request.grade,
            classNum = request.classNum,
            number = request.number,
            profileImagePath = request.profileImagePath
        )

    private fun documentWithUpdatedWriterInfo(
        document: Document,
        request: UpdateWriterInfoRequest,
    ): Document {

        val major = majorRepository.findByIdOrNull(request.majorId) ?: throw MajorNotFoundException

        return request.run {
            document.updateWriterInfo(
                document.writer.updateVariableInfo(
                    grade = grade,
                    classNum = classNum,
                    number = number,
                    email = email,
                    profileImagePath = profileImagePath,
                    major = major
                )
            )
        }
    }
}