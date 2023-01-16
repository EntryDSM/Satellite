package kr.hs.entrydsm.exit.domain.document.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator
import kr.hs.entrydsm.exit.common.afterContainer
import kr.hs.entrydsm.exit.common.getTestDocument
import kr.hs.entrydsm.exit.domain.document.exception.IllegalStatusException
import kr.hs.entrydsm.exit.domain.document.persistence.Document
import kr.hs.entrydsm.exit.domain.document.persistence.enums.Status
import kr.hs.entrydsm.exit.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.exit.domain.student.persistence.Student
import kr.hs.entrydsm.exit.global.security.SecurityUtil

internal class CancelSubmitMyDocumentUseCaseTest : DescribeSpec({

    val documentRepository: DocumentRepository = mockk()
    mockkObject(SecurityUtil)

    val cancelSubmitMyDocumentUseCase = CancelSubmitMyDocumentUseCase(documentRepository)

    describe("cancelSubmitMyDocumentUseCase") {

        val student = AnyValueObjectGenerator.anyValueObject<Student>()
        val document = getTestDocument(
            student = student,
            status = Status.SUBMITTED
        )

        context("SUBMITTED 상태의 문서를 가진 학생(유저)가 주어지면") {

            val slot = slot<Document>()

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns document
            every { documentRepository.save(capture(slot)) } returnsArgument 0

            it("CREATED 상태로 변경하여 저장한다.") {

                cancelSubmitMyDocumentUseCase.execute()

                verify(exactly = 1) { documentRepository.save(slot.captured) }
                slot.captured.status shouldBe Status.CREATED
            }
        }

        val createdDocument = getTestDocument(
            student = student,
            status = Status.CREATED
        )

        context("CREATED 상태의 문서를 가진 학생(유저)가 주어지면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns createdDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<IllegalStatusException> {
                    cancelSubmitMyDocumentUseCase.execute()
                }
                verify(exactly = 0) { documentRepository.save(any()) }
            }
        }

        val sharedDocument = getTestDocument(
            student = student,
            status = Status.SHARED
        )

        context("SHARED 상태의 문서를 가진 학생(유저)가 주어지면") {

            every { SecurityUtil.getCurrentStudent() } returns student
            every { documentRepository.findByWriterStudentId(student.id) } returns sharedDocument

            it("IllegalStatus 예외를 던진다.") {

                shouldThrow<IllegalStatusException> {
                    cancelSubmitMyDocumentUseCase.execute()
                }
                verify(exactly = 0) { documentRepository.save(any()) }
            }
        }
    }

    afterContainer()
})
