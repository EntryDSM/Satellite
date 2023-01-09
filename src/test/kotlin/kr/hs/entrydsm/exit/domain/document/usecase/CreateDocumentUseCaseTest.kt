package kr.hs.entrydsm.exit.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.common.afterContainer
import kr.hs.entrydsm.exit.common.getTestDocument
import kr.hs.entrydsm.exit.domain.document.exception.DocumentAlreadyExistException
import kr.hs.entrydsm.exit.domain.document.exception.MajorNotFoundException
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.CreateDocumentRequest
import kr.hs.entrydsm.exit.domain.major.persistence.Major
import kr.hs.entrydsm.exit.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.global.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull

internal class CreateDocumentUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    val majorRepository: MajorRepository = mockk()
    mockkObject(SecurityUtil)

    val createDocumentUseCase = CreateDocumentUseCase(documentRepository, majorRepository)

    describe("createDocument") {

        val student = anyValueObject<Student>()
        val major = anyValueObject<Major>()
        val document = getTestDocument(student, major)

        val request = CreateDocumentRequest(major.id)

        context("아직 문서를 생성하지 않은 학생과 전공의 정보가 주어지면") {

            every { SecurityUtil.getCurrentStudent() } returns student
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

    afterContainer()
})