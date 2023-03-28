package kr.hs.entrydsm.satellite.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kr.hs.entrydsm.satellite.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.satellite.common.getTestDocument
import kr.hs.entrydsm.satellite.domain.document.exception.DocumentAlreadyExistException
import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.domain.document.presentation.dto.request.CreateDocumentRequest
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.persistence.Major
import kr.hs.entrydsm.satellite.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.satellite.domain.school.facade.SchoolYearFacade
import kr.hs.entrydsm.satellite.domain.student.persistence.Student
import kr.hs.entrydsm.satellite.common.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull

internal class CreateDocumentUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    val majorRepository: MajorRepository = mockk()
    val schoolYearFacade: SchoolYearFacade = mockk()
    mockkObject(SecurityUtil)

    val createDocumentUseCase = CreateDocumentUseCase(documentRepository, majorRepository, schoolYearFacade)

    describe("createDocument") {

        val student = anyValueObject<Student>(
            "grade" to "1",
            "classNum" to "1",
            "number" to "1"
        )
        val major = anyValueObject<Major>()
        val document = getTestDocument(student, major)

        val request = CreateDocumentRequest(major.id)

        context("아직 문서를 생성하지 않은 학생과 전공의 정보가 주어지면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { schoolYearFacade.getSchoolYear() } returns 2023
            every { documentRepository.findByWriterStudentId(student.id) } returns null
            every { majorRepository.findByIdOrNull(request.majorId) } returns major
            every { documentRepository.save(any()) } returns document

            it("문서를 생성한다.") {

                val response = createDocumentUseCase.execute(request)
                response.documentId shouldBe document.id
            }
        }

        context("이미 문서를 생성한 학생과 전공의 정보가 주어지면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns document
            every { majorRepository.findByIdOrNull(request.majorId) } returns major
            every { documentRepository.save(any()) } returns document

            it("DocumentAlreadyExist 예외를 던진다.") {

                shouldThrow<DocumentAlreadyExistException> {
                    createDocumentUseCase.execute(request)
                }
            }
        }

        context("주어진 Major의 id가 잘못되었으면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns null
            every { majorRepository.findByIdOrNull(request.majorId) } returns null

            it("MajorNotFound 예외를 던진다.") {

                shouldThrow<MajorNotFoundException> {
                    createDocumentUseCase.execute(request)
                }
            }
        }
    }
})